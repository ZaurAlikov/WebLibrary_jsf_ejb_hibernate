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
    private String searchType;
    private static LinkedHashMap<String, SearchTypeEnum> searchTypeList = new LinkedHashMap<String, SearchTypeEnum>();
    private List<BookEntity> books;

    @PersistenceContext(unitName = "libraryPU")
    private EntityManager entityManager;

    @PostConstruct
    public void postConstruct() {
        this.fillAllBooks();
        ResourceBundle res = ResourceBundle.getBundle("nls/message", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        searchTypeList.put(res.getString("type_search_name"), SearchTypeEnum.Название);
        searchTypeList.put(res.getString("type_search_author"), SearchTypeEnum.Автор);
    }

    public String getSearchType() {
        return searchType;
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

        Query query = entityManager.createQuery("select book from BookEntity book where genre.id = :id");
        query.setParameter("id", genreId);
        books = query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public void fillAllBooks(){
        Query query = entityManager.createQuery("select book from BookEntity book order by book.name");
        books = query.getResultList();
    }

    public byte[] getImage(long id){
        BookEntity book = entityManager.find(BookEntity.class, id);
        return book.getImage();
    }

}
