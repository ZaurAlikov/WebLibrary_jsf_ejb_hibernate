package ru.alikovzaur.library.controllers;

import ru.alikovzaur.library.entityes.GenreEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class GenresController implements Serializable {

    private int id;
    private String name;
    private List genresList = new ArrayList<GenreEntity>();

    @PersistenceContext(unitName = "libraryPU")
    private EntityManager entityManager;

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

    @SuppressWarnings("unchecked")
    private List<GenreEntity> getGenres(){
        Query query = entityManager.createQuery("select genres from GenreEntity genres order by name");
        genresList = query.getResultList();
        return genresList;
    }

    @SuppressWarnings("unchecked")
    public List<GenreEntity> getGenresList() throws Exception {
        if (!genresList.isEmpty()) {
            return genresList;
        } else {
            return getGenres();
        }
    }

}
