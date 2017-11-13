package ru.alikovzaur.library.controllers;

import ru.alikovzaur.library.entityes.AuthorEntity;
import ru.alikovzaur.library.interfaces.BookDAO;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@ApplicationScoped
public class AuthorController implements Converter {

    private Map<String, AuthorEntity> map;
    private List<SelectItem> selectItems = new ArrayList<>();

    @EJB
    private BookDAO bookDao;

    @PostConstruct
    public void postConstruct() {

        map = new HashMap<>();

        for (AuthorEntity author : bookDao.getAuthors()) {
            map.put(author.getFio(), author);
            selectItems.add(new SelectItem(author, author.getFio()));
        }
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
