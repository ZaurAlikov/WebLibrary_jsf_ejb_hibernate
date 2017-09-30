package ru.alikovzaur.library.entityes;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "publisher", schema = "library")
public class PublisherEntity {
    private long id;
    private String name;
    private Set<BookEntity> bookEntities;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "publisher")
    public Set<BookEntity> getBookEntities() {
        return bookEntities;
    }

    public void setBookEntities(Set<BookEntity> bookEntities) {
        this.bookEntities = bookEntities;
    }
}
