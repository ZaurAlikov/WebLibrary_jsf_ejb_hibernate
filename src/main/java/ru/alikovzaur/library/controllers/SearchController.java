package ru.alikovzaur.library.controllers;

import ru.alikovzaur.library.entityes.BookEntity;
import ru.alikovzaur.library.enums.SearchTypeEnum;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.*;

@Named
@SessionScoped
public class SearchController implements Serializable {
    private Long selectedGenre;
    private String searchType;
    private String searchField;
    private static LinkedHashMap<String, SearchTypeEnum> searchTypeList = new LinkedHashMap<String, SearchTypeEnum>();
    private List<BookEntity> books;

    @PersistenceContext(unitName = "libraryPU")
    private EntityManager entityManager;

    @PostConstruct
    public void postConstruct() {
        this.fillAllBooks();
        setSelectedGenre(-1L);
        ResourceBundle res = ResourceBundle.getBundle("nls/message", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        searchTypeList.put(res.getString("type_search_name"), SearchTypeEnum.Название);
        searchTypeList.put(res.getString("type_search_author"), SearchTypeEnum.Автор);
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getSearchField() {
        return searchField;
    }

    public Long getSelectedGenre() {
        return selectedGenre;
    }

    public void setSelectedGenre(Long selectedGenre) {
        this.selectedGenre = selectedGenre;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public LinkedHashMap<String, SearchTypeEnum> getSearchTypeList(){
        return searchTypeList;
    }

    public List<BookEntity> getBooks() {
        return books;
    }

    public void setBooks(List<BookEntity> books) {
        this.books = books;
    }

    @SuppressWarnings("unchecked")
    public void fillBooksByGenre(){
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Long genreId = Long.valueOf(params.get("genre_id"));

        Query query = entityManager.createQuery("select book from BookEntity book where genre.id = :id order by book.name");
        query.setParameter("id", genreId);
        books = query.getResultList();

        setSelectedGenre(genreId);
        setSearchField("");
    }

    @SuppressWarnings("unchecked")
    public void fillAllBooks(){
        Query query = entityManager.createQuery("select book from BookEntity book order by book.name");
//        query.setMaxResults(5);
//        query.setFirstResult(0);
        books = query.getResultList();
        setSearchField("");
        setSelectedGenre(0L);
    }

    public byte[] getImage(long id){
        BookEntity book = entityManager.find(BookEntity.class, id);
        return book.getImage();
    }

    @SuppressWarnings("unchecked")
    public void fillBooksBySearch(){
        if (searchType.equals(SearchTypeEnum.Название.toString())){
            Query query = entityManager.createQuery("select book from BookEntity book where book.name like concat('%', :bookName, '%') order by book.name");
            query.setParameter("bookName", searchField);
            books = query.getResultList();
            setSelectedGenre(-1L);
        } else if (searchType.equals(SearchTypeEnum.Автор.toString())){
            Query query = entityManager.createQuery("select book from BookEntity book where book.author.fio like concat('%', :author, '%') order by book.name");
            query.setParameter("author", searchField);
            books = query.getResultList();
            setSelectedGenre(-1L);
        }
    }
}
