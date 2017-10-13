package ru.alikovzaur.library.controllers;

import ru.alikovzaur.library.entityes.AuthorEntity;
import ru.alikovzaur.library.entityes.BookEntity;
import ru.alikovzaur.library.entityes.PublisherEntity;
import ru.alikovzaur.library.interfaces.BookDAO;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import javax.xml.bind.ValidationEvent;
import java.io.Serializable;
import java.util.*;

@Named
@SessionScoped
public class BookController implements Serializable {

    private String bookName;
    private String author;
    private int bookPageCount;
    private String publisher;
    private int publishYear;
    private String isbn;
    private Long selectedGenre;
    private String searchType;
    private String searchField;
    private List<BookEntity> books;
    private int bookOnPage;
    private ArrayList<Integer> pageCount;
    private String typeSearch;
    private Long genreId;
    private int bookCount;
    private int selectedPage;
    private HashMap<Integer, List<BookEntity>> booksMap;

    @EJB
    private BookDAO bookDao;

    @PostConstruct
    public void postConstruct() {
        this.booksMap = new HashMap<>();
        this.selectedGenre = -1L;
        this.bookCount = 0;
        this.selectedPage = 1;
        this.bookOnPage = 5;
        this.books = new ArrayList<>();
        this.pageCount = new ArrayList<>();
        this.typeSearch = "all";
        this.genreId = -1L;
        this.fillBooks();
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getBookPageCount() {
        return bookPageCount;
    }

    public void setBookPageCount(int bookPageCount) {
        this.bookPageCount = bookPageCount;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public Long getSelectedGenre() {
        return selectedGenre;
    }

    public void setSelectedGenre(Long selectedGenre) {
        this.selectedGenre = selectedGenre;
    }

    public List<BookEntity> getBooks() {
        return books;
    }

    public void setBooks(List<BookEntity> books) {
        this.books = books;
    }

    public ArrayList<Integer> getPageCount() {
        return pageCount;
    }

    public void setPageCount(ArrayList<Integer> pageCount) {
        this.pageCount = pageCount;
    }

    public int getBookCount() {
        return bookCount;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }

    public int getSelectedPage() {
        return selectedPage;
    }

    public void setSelectedPage(int selectedPage) {
        this.selectedPage = selectedPage;
    }

    public int getBookOnPage() {
        return bookOnPage;
    }

    public void setBookOnPage(int bookOnPage) {
        this.bookOnPage = bookOnPage;
    }

    public byte[] getImage(long id){
        return bookDao.getImage(id);
    }

    public byte[] getPdf(long id){
        return bookDao.getPdf(id);
    }

    public void fillBooks(){
        selectedPage = 1;
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        if (params.size() > 0){
            if(params.get("type_search") != null){
                typeSearch = params.get("type_search");
            }
            if (params.get("selected_page") != null){
                selectedPage = Integer.valueOf(params.get("selected_page"));
            }
            if (params.get("genre_id") != null){
                genreId = Long.valueOf(params.get("genre_id"));
            }
        }

        int firstResult = selectedPage * bookOnPage - bookOnPage;
        booksMap.clear();
        booksMap = bookDao.getBooks(typeSearch, searchType, searchField, selectedPage, genreId, bookOnPage, firstResult);
        books = booksMap.values().iterator().next();
        bookCount = booksMap.keySet().iterator().next();
        switch (typeSearch) {
            case "all": {
                setSearchField("");
                setSelectedGenre(0L);
                break;
            }
            case "genre": {
                setSelectedGenre(genreId);
                setSearchField("");
                break;
            }
            case "search": {
                setSelectedGenre(-1L);
                break;
            }
        }
        createPages();
    }

    private void createPages(){
        int pages = 0;
        if (bookCount > 0 && bookOnPage > 0){
            pages = (int) Math.ceil((double)bookCount / bookOnPage);
        }

        pageCount.clear();
        for (int i = 1; i <= pages; i++){
            pageCount.add(i);
        }
    }

    public void booksPageChanged(ValueChangeEvent event){
        selectedPage = 1;
        bookOnPage = Integer.valueOf(event.getNewValue().toString());
        fillBooks();
    }

    public void editBook(long id){
        for (BookEntity b : books){
            if (b.getId() == id){
                b.setEdit(true);
                bookName = b.getName();
                author = b.getAuthor().getFio();
                bookPageCount = b.getPageCount();
                publisher = b.getPublisher().getName();
                publishYear = b.getPublishYear();
                isbn = b.getIsbn();
            }
        }
    }

    public void saveBook(long id){
        for (BookEntity b : books){
            if (b.getId() == id){
                b.setEdit(false);
                b.setName(bookName);
                AuthorEntity authorEntity = b.getAuthor();
                authorEntity.setFio(author);
                b.setAuthor(authorEntity);
                b.setPageCount(bookPageCount);
                PublisherEntity publisherEntity = b.getPublisher();
                publisherEntity.setName(publisher);
                b.setPublisher(publisherEntity);
                b.setPublishYear(publishYear);
                b.setIsbn(isbn);
                bookDao.updateBook(b);
            }
        }
    }
}
