package ru.alikovzaur.library.DAO;

import ru.alikovzaur.library.entityes.BookEntity;
import ru.alikovzaur.library.entityes.GenreEntity;
import ru.alikovzaur.library.entityes.PublisherEntity;
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

    @PersistenceContext(unitName = "libraryPU")
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public HashMap<Long, List<BookEntity>> getBooks(String typeSearch, String searchType, String searchField, int selectedPage, long genreId, int maxResults, int firstResult) {

        HashMap<Long, List<BookEntity>> booksMap = new HashMap<>();
        List<BookEntity> books = new ArrayList<>();
        long bookCount = 0;
        Query query = null;

        switch (typeSearch) {
            case "all": {
                query = entityManager.createQuery("select book from BookEntity book join fetch book.author join fetch book.publisher join fetch book.genre order by book.name");
                bookCount = (long) entityManager.createQuery("select count(book) from BookEntity book").getSingleResult();
                break;
            }
            case "genre": {
                query = entityManager.createQuery("select book from BookEntity book join fetch book.author join fetch book.publisher join fetch book.genre where book.genre.id = :id order by book.name");
                query.setParameter("id", genreId);
                bookCount = (long) entityManager.createQuery("select count(book) from BookEntity book where genre.id = :id").setParameter("id", genreId).getSingleResult();
                break;
            }
            case "search": {
                if (searchType.equals(SearchTypeEnum.Название.toString())) {
                    query = entityManager.createQuery("select book from BookEntity book join fetch book.author join fetch book.publisher join fetch book.genre where book.name like concat('%', :bookName, '%') order by book.name");
                    query.setParameter("bookName", searchField);
                    bookCount = (long) entityManager.createQuery("select count(book) from BookEntity book where book.name like concat('%', :bookName, '%')").setParameter("bookName", searchField).getSingleResult();
                } else if (searchType.equals(SearchTypeEnum.Автор.toString())) {
                    query = entityManager.createQuery("select book from BookEntity book join fetch book.author join fetch book.publisher join fetch book.genre where book.author.fio like concat('%', :author, '%') order by book.name");
                    query.setParameter("author", searchField);
                    bookCount = (long) entityManager.createQuery("select count(book) from BookEntity book where book.author.fio like concat('%', :author, '%')").setParameter("author", searchField).getSingleResult();
                }
                break;
            }
        }
        if (query != null) {
//            bookCount = query.getResultList().size();
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

    @Override
    public void delBook(BookEntity book) {
        entityManager.remove(entityManager.merge(book));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<GenreEntity> getGenres() {
        Query query = entityManager.createQuery("select genres from GenreEntity genres order by name");
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PublisherEntity> getPublishers() {
        Query query = entityManager.createQuery("select publisher from PublisherEntity publisher order by name");
        return query.getResultList();
    }
}
