package ru.alikovzaur.library.controllers;

import ru.alikovzaur.library.entityes.GenreEntity;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class GenresController implements Serializable {

    private int id;
    private String name;
    private static List genresList = new ArrayList<GenreEntity>();

    @PersistenceContext(unitName = "libraryPU")
    EntityManager entityManager;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String genre) {
        this.name = genre;
    }

    private List<GenreEntity> getGenres(){
        Query query = entityManager.createQuery("select genres from GenreEntity genres");
        genresList = query.getResultList();
        return genresList;
    }

    public List<GenreEntity> getGenresList() throws Exception {
        if (!genresList.isEmpty()) {
            return genresList;
        } else {
            return getGenres();
        }
    }

}
