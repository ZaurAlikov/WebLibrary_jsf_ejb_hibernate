package ru.alikovzaur.library.entityes;

import javax.persistence.*;

@Entity
@Table(name = "rating", schema = "library")
@IdClass(RatingEntityPK.class)
public class RatingEntity {
    private String username;
    private long bookId;
    private Integer rating;

    @Id
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Id
    @Column(name = "book_id")
    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    @Basic
    @Column(name = "rating")
    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RatingEntity that = (RatingEntity) o;

        if (bookId != that.bookId) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (rating != null ? !rating.equals(that.rating) : that.rating != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (int) (bookId ^ (bookId >>> 32));
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        return result;
    }
}
