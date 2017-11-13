package ru.alikovzaur.library.entityes;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class RatingEntityPK implements Serializable {
    private String username;
    private long bookId;

    @Column(name = "username")
    @Id
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "book_id")
    @Id
    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RatingEntityPK that = (RatingEntityPK) o;

        if (bookId != that.bookId) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (int) (bookId ^ (bookId >>> 32));
        return result;
    }
}
