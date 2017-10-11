package ru.alikovzaur.library.interfaces;

import ru.alikovzaur.library.entityes.BookEntity;

import javax.ejb.Local;
import java.util.HashMap;
import java.util.List;

@Local
public interface BookDAO {
    HashMap<Integer, List<BookEntity>> getBooks(String typeSearch, String searchType, String searchField, int selectedPage, long genreId, int maxResults, int firstResult);

    void updateBook(BookEntity book);

    byte[] getImage(long id);

    byte[] getPdf(long id);
}
