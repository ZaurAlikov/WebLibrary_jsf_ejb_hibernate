package ru.alikovzaur.library.interfaces;

import ru.alikovzaur.library.entityes.BookEntity;
import ru.alikovzaur.library.entityes.GenreEntity;
import ru.alikovzaur.library.entityes.PublisherEntity;

import javax.ejb.Local;
import java.util.HashMap;
import java.util.List;

@Local
public interface BookDAO {
    HashMap<Long, List<BookEntity>> getBooks(String typeSearch, String searchType, String searchField, int selectedPage, long genreId, int maxResults, int firstResult);

    void updateBook(BookEntity book);

    void delBook(BookEntity book);

    byte[] getImage(long id);

    byte[] getPdf(long id);

    List<GenreEntity> getGenres();

    List<PublisherEntity> getPublishers();
}
