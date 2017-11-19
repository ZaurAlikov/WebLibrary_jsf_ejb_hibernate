package ru.alikovzaur.library.controllers;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import ru.alikovzaur.library.entityes.*;
import ru.alikovzaur.library.interfaces.BookDAO;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.*;

@Named
@SessionScoped
public class BookController implements Serializable {

    private Long selectedGenre;
    private String searchType;
    private String searchField;
    private List<BookEntity> books;
    private int bookOnPage;
    private ArrayList<Integer> pageCount;
    private String typeSearch;
    private Long genreId;
    private long bookCount;
    private int selectedPage;
    private HashMap<Integer, Object> booksMap;
    private BookEntity selectedBook;
    private List<Long> listId;
    private boolean contentIsEdited;
    private boolean imageIsEdited;
    private String bookFlag;
    private ResourceBundle bundle;

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
        this.contentIsEdited = false;
        this.imageIsEdited = false;
        this.bundle = ResourceBundle.getBundle("nls/message", FacesContext.getCurrentInstance().getViewRoot().getLocale());
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

    public long getBookCount() {
        return bookCount;
    }

    public void setBookCount(long bookCount) {
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

    public BookEntity getSelectedBook() {
        return selectedBook;
    }

    public void setSelectedBook(BookEntity selectedBook) {
        this.selectedBook = selectedBook;
    }

    public String getBookFlag() {
        return bookFlag;
    }

    public void setBookFlag(String bookFlag) {
        this.bookFlag = bookFlag;
    }

    public byte[] getImage(long id){
//        return bookDao.getImage(id);
        byte[] bytes = new byte[0];
        for (BookEntity b : books) {
            if (b.getId() == id){
                bytes = b.getImage();
            }
        }
        return bytes;
    }

    public byte[] getPdf(long id){
        return bookDao.getPdf(id);
    }

    @SuppressWarnings("unchecked")
    public  void fillBooks(int selPage){
        selectedPage = selPage;
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        if (params.size() > 0){
            if(params.get("type_search") != null){
                typeSearch = params.get("type_search");
            }
            if (params.get("genre_id") != null){
                genreId = Long.valueOf(params.get("genre_id"));
            }
        }

        int firstResult = selectedPage * bookOnPage - bookOnPage;
        booksMap.clear();
        booksMap = bookDao.getBooks(typeSearch, searchType, searchField, selectedPage, genreId, bookOnPage, firstResult);
        books = (List<BookEntity>) booksMap.get(1);
        bookCount = (long) booksMap.get(2);
        listId = (List<Long>) booksMap.get(3);
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
        if(books.size() == 0 && selectedPage > 1){
            fillBooks(selectedPage - 1);
        }
    }

    public void fillBooks(){
        fillBooks(1);
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

    public void saveBook(){

        if(!isValidate()){
            return;
        }

        if(bookFlag.equals("edit")){
            bookDao.updateBook(selectedBook, contentIsEdited, imageIsEdited);
            contentIsEdited = false;
            imageIsEdited = false;
        }
        if(bookFlag.equals("add")){
            bookDao.addBook(selectedBook);
        }
        RequestContext.getCurrentInstance().execute("PF('edit_dlg').hide();");
        fillBooks(selectedPage);
    }

    public void addNewBook(){
        selectedBook = new BookEntity();
    }

    public void dropSelBook(){
        selectedBook = null;
    }

    public void delBook(){
        bookDao.delBook(selectedBook);
        fillBooks(selectedPage);
    }

    public void setRating(ValueChangeEvent e){
        HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String username = httpServletRequest.getRemoteUser();
        int rating = (int) e.getNewValue();
        RatingEntity ratingEntity = new RatingEntity();
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        long id = 0;
        if (params.size() > 0){
            if(params.get("id") != null){
                id = Long.valueOf(params.get("id"));
            }
        }
        ratingEntity.setBookId(id);
        ratingEntity.setUsername(username);
        ratingEntity.setRating(rating);
        bookDao.setBookRating(ratingEntity);
        fillBooks(selectedPage);
    }

    @SuppressWarnings("unchecked")
    public boolean getReadOnly(long book_id){
        int count = (int) listId.stream().filter(b -> b == book_id).count();
        return count != 0;
    }

    public void handleFileUpload(FileUploadEvent event) {
        String id = event.getComponent().getId();
        if(id.equals("image")){
            selectedBook.setImage(event.getFile().getContents());
            imageIsEdited = true;
        }
        if(id.equals("pdf")){
            selectedBook.setContent(event.getFile().getContents());
            contentIsEdited = true;
        }
    }

    private boolean isValidate(){
        if(isNullOrEmpty(selectedBook.getName())
                || isNullOrEmpty(selectedBook.getAuthor())
                || isNullOrEmpty(selectedBook.getGenre())
                || isNullOrEmpty(selectedBook.getPageCount())
                || isNullOrEmpty(selectedBook.getPublisher())
                || isNullOrEmpty(selectedBook.getPublishYear())
                || isNullOrEmpty(selectedBook.getIsbn())
                || isNullOrEmpty(selectedBook.getDescr())){
            failValidation(bundle.getString("error_empty_fields"));
            return false;
        }
        if(bookFlag.equals("add")){
            if(isNullOrEmpty(selectedBook.getContent())){
                failValidation(bundle.getString("error_load_pdf"));
                return false;
            }
            if(isNullOrEmpty(selectedBook.getImage())){
                failValidation(bundle.getString("error_load_img"));
                return false;
            }
        }
        return true;
    }

    private boolean isNullOrEmpty(Object obj){
        return obj == null || obj.toString().equals("") || obj.equals(0);
    }

    private void failValidation(String mess){
        FacesContext.getCurrentInstance().validationFailed();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mess, bundle.getString("error_empty_fields")));
    }
}
