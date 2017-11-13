package ru.alikovzaur.library.DAO;

import ru.alikovzaur.library.entityes.*;
import ru.alikovzaur.library.enums.SearchTypeEnum;
import ru.alikovzaur.library.interfaces.BookDAO;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
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
    public HashMap<Integer, Object> getBooks(String typeSearch, String searchType, String searchField, int selectedPage, long genreId, int maxResults, int firstResult) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String username = httpServletRequest.getRemoteUser();
        HashMap<Integer, Object> booksMap = new HashMap<>();
        List<BookEntity> books = new ArrayList<>();
        long bookCount = 0;
        Query query = null;
        List<Long> bookIds = null;

        switch (typeSearch) {
            case "all": {
                query = entityManager.createQuery("select book from BookEntity book join fetch book.author join fetch book.publisher join fetch book.genre order by book.name");
                bookCount = (long) entityManager.createQuery("select count(book) from BookEntity book").getSingleResult();
                bookIds = entityManager.createQuery("select r.bookId from RatingEntity r where r.username = :user").setParameter("user", username).getResultList();
                break;
            }
            case "genre": {
                query = entityManager.createQuery("select book from BookEntity book join fetch book.author join fetch book.publisher join fetch book.genre where book.genre.id = :id order by book.name");
                query.setParameter("id", genreId);
                bookCount = (long) entityManager.createQuery("select count(book) from BookEntity book where genre.id = :id").setParameter("id", genreId).getSingleResult();
                bookIds = entityManager.createQuery("select r.bookId from RatingEntity r where r.username = :user").setParameter("user", username).getResultList();
                break;
            }
            case "search": {
                if (searchType.equals(SearchTypeEnum.Название.toString())) {
                    query = entityManager.createQuery("select book from BookEntity book join fetch book.author join fetch book.publisher join fetch book.genre where book.name like concat('%', :bookName, '%') order by book.name");
                    query.setParameter("bookName", searchField);
                    bookCount = (long) entityManager.createQuery("select count(book) from BookEntity book where book.name like concat('%', :bookName, '%')").setParameter("bookName", searchField).getSingleResult();
                    bookIds = entityManager.createQuery("select r.bookId from RatingEntity r where r.username = :user").setParameter("user", username).getResultList();
                } else if (searchType.equals(SearchTypeEnum.Автор.toString())) {
                    query = entityManager.createQuery("select book from BookEntity book join fetch book.author join fetch book.publisher join fetch book.genre where book.author.fio like concat('%', :author, '%') order by book.name");
                    query.setParameter("author", searchField);
                    bookCount = (long) entityManager.createQuery("select count(book) from BookEntity book where book.author.fio like concat('%', :author, '%')").setParameter("author", searchField).getSingleResult();
                    bookIds = entityManager.createQuery("select r.bookId from RatingEntity r where r.username = :user").setParameter("user", username).getResultList();
                }
                break;
            }
        }
        if (query != null) {
            query.setMaxResults(maxResults);
            query.setFirstResult(firstResult);
            books.clear();
            books = query.getResultList();
        }
        booksMap.put(1, books);
        booksMap.put(2, bookCount);
        booksMap.put(3, bookIds);
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
    public void addBook(BookEntity book) {
        entityManager.persist(book);
    }

    @Override
    public void updateBook(BookEntity book, boolean contEdited, boolean imgEdited) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("update BookEntity ");
        queryBuilder.append("set name = :name, ");
        queryBuilder.append("pageCount = :pageCount, ");
        queryBuilder.append("isbn = :isbn, ");
        queryBuilder.append("publishYear = :publishYear, ");
        queryBuilder.append("descr = :descr, ");
        queryBuilder.append("author = :author, ");
        queryBuilder.append("genre = :genre, ");
        if(contEdited) queryBuilder.append("content = :content, ");
        if(imgEdited) queryBuilder.append("image = :image, ");
        queryBuilder.append("publisher = :publisher ");
        queryBuilder.append("where id = :id ");

        Query query = entityManager.createQuery(queryBuilder.toString());
        query.setParameter("name", book.getName());
        query.setParameter("pageCount", book.getPageCount());
        query.setParameter("isbn", book.getIsbn());
        query.setParameter("publishYear", book.getPublishYear());
        query.setParameter("descr", book.getDescr());
        query.setParameter("author", book.getAuthor());
        query.setParameter("genre", book.getGenre());
        if(contEdited) query.setParameter("content", book.getContent());
        if(imgEdited) query.setParameter("image", book.getImage());
        query.setParameter("publisher", book.getPublisher());
        query.setParameter("id", book.getId());

        query.executeUpdate();
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

    @Override
    @SuppressWarnings("unchecked")
    public List<AuthorEntity> getAuthors() {
        Query query = entityManager.createQuery("select author from AuthorEntity author order by fio");
        return query.getResultList();
    }

    @Override
    public void setBookRating(RatingEntity ratingEntity) {
        entityManager.persist(ratingEntity);
    }
}
