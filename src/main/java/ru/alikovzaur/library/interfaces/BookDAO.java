package ru.alikovzaur.library.interfaces;

import ru.alikovzaur.library.entityes.*;

import javax.ejb.Local;
import java.util.HashMap;
import java.util.List;

@Local
public interface BookDAO {
    HashMap<Integer, Object> getBooks(String typeSearch, String searchType, String searchField, int selectedPage, long genreId, int maxResults, int firstResult);

    void addBook(BookEntity book);

    void updateBook(BookEntity book, boolean contEdited, boolean imgEdited);

    void delBook(BookEntity book);

    byte[] getImage(long id);

    byte[] getPdf(long id);

    void setBookRating(RatingEntity ratingEntity);

    List<GenreEntity> getGenres();

    List<PublisherEntity> getPublishers();

    List<AuthorEntity> getAuthors();
}
