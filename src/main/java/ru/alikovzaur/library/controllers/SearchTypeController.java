package ru.alikovzaur.library.controllers;

import ru.alikovzaur.library.enums.SearchTypeEnum;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

@Named
@RequestScoped
public class SearchTypeController {
    private static LinkedHashMap<String, SearchTypeEnum> searchTypeList = new LinkedHashMap<String, SearchTypeEnum>();

    @PostConstruct
    public void postConstruct() {
        ResourceBundle res = ResourceBundle.getBundle("nls/message", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        searchTypeList.clear();
        searchTypeList.put(res.getString("type_search_name"), SearchTypeEnum.Название);
        searchTypeList.put(res.getString("type_search_author"), SearchTypeEnum.Автор);
    }

    public LinkedHashMap<String, SearchTypeEnum> getSearchTypeList() {
        return searchTypeList;
    }
}
