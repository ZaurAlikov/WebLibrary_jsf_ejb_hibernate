package ru.alikovzaur.library.DAO;

import ru.alikovzaur.library.entityes.BookEntity;
import ru.alikovzaur.library.enums.SearchTypeEnum;
import ru.alikovzaur.library.interfaces.BookDAO;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Stateless
public class BookDaoImpl implements BookDAO, Serializable {
//    private HashMap<Integer, List<BookEntity>> booksMap;
//    private List<BookEntity> books;
//    private int bookCount;
//    private Query query;

    @PersistenceContext(unitName = "libraryPU")
    private EntityManager entityManager;

//    @PostConstruct
//    public void postConstruct() {
//        this.bookCount = 0;
//        this.query = null;
//        this.books = new ArrayList<>();
//        this.booksMap = new HashMap<>();
//    }

    @Override
    @SuppressWarnings("unchecked")
    public HashMap<Integer, List<BookEntity>> getBooks(String typeSearch, String searchType, String searchField, int selectedPage, long genreId, int maxResults, int firstResult) {

        HashMap<Integer, List<BookEntity>> booksMap = new HashMap<>();
        List<BookEntity> books = new ArrayList<>();
        int bookCount = 0;
        Query query = null;

        switch (typeSearch) {
            case "all": {
                query = entityManager.createQuery("select book from BookEntity book order by book.name");
                break;
            }
            case "genre": {
                query = entityManager.createQuery("select book from BookEntity book where genre.id = :id order by book.name");
                query.setParameter("id", genreId);
                break;
            }
            case "search": {
                if (searchType.equals(SearchTypeEnum.Название.toString())) {
                    query = entityManager.createQuery("select book from BookEntity book where book.name like concat('%', :bookName, '%') order by book.name");
                    query.setParameter("bookName", searchField);
                } else if (searchType.equals(SearchTypeEnum.Автор.toString())) {
                    query = entityManager.createQuery("select book from BookEntity book where book.author.fio like concat('%', :author, '%') order by book.name");
                    query.setParameter("author", searchField);
                }
                break;
            }
        }
        if (query != null && query.getResultList() != null) {
            bookCount = query.getResultList().size();
            query.setMaxResults(maxResults);
            query.setFirstResult(firstResult);
            books.clear();
            books = query.getResultList();
        }

        booksMap.put(bookCount, books);

        return booksMap;
    }

    @Override
    public byte[] getImage(long id){
        BookEntity book = entityManager.find(BookEntity.class, id);
        return book.getImage();
    }

    @Override
    public byte[] getPdf(long id){
        BookEntity book = entityManager.find(BookEntity.class, id);
        return book.getContent();
    }

    @Override
    public void updateBook(BookEntity book) {
        entityManager.merge(book);
    }
}
