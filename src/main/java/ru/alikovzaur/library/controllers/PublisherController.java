package ru.alikovzaur.library.controllers;

import ru.alikovzaur.library.entityes.GenreEntity;
import ru.alikovzaur.library.entityes.PublisherEntity;
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
public class PublisherController implements Converter {

    private Map<String, PublisherEntity> map;
    private List<SelectItem> selectItems = new ArrayList<>();

    @EJB
    private BookDAO bookDao;

    @PostConstruct
    public void postConstruct() {

        map = new HashMap<>();

        for (PublisherEntity publisher : bookDao.getPublishers()) {
            map.put(publisher.getName(), publisher);
            selectItems.add(new SelectItem(publisher, publisher.getName()));
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
