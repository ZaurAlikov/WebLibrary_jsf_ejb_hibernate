package ru.alikovzaur.library.controllers;

import ru.alikovzaur.library.entityes.GenreEntity;
import ru.alikovzaur.library.interfaces.BookDAO;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@Named
@ApplicationScoped
public class GenresController implements Serializable, Converter {

    private int id;
    private String name;
    private List<GenreEntity> genresList;
    private Map<String, GenreEntity> map;
    private List<SelectItem> selectItems = new ArrayList<>();

    @EJB
    private BookDAO bookDao;

    @PostConstruct
    public void postConstruct() {

        map = new HashMap<>();
        genresList = bookDao.getGenres();

        for (GenreEntity genre : genresList) {
            map.put(genre.getName(), genre);
            selectItems.add(new SelectItem(genre, genre.getName()));
        }
    }

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

    public List<GenreEntity> getGenresList(){
        return genresList;
    }

    public List<SelectItem> getSelectItems() {
        return selectItems;
    }

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return map.get(s);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return o.toString();
    }
}
