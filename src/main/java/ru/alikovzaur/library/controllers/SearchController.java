package ru.alikovzaur.library.controllers;

import ru.alikovzaur.library.enums.SearchTypeEnum;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

@Named
@SessionScoped
public class SearchController implements Serializable {
    private String searchType;
    private static LinkedHashMap<String, SearchTypeEnum> searchTypeList = new LinkedHashMap<String, SearchTypeEnum>();


    public SearchController(){
        ResourceBundle res = ResourceBundle.getBundle("nls/message", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        searchTypeList.put(res.getString("type_search_name"), SearchTypeEnum.Название);
        searchTypeList.put(res.getString("type_search_author"), SearchTypeEnum.Автор);
    }

    public String getSearchType() {
        return searchType;
    }

    public LinkedHashMap<String, SearchTypeEnum> getSearchTypeList(){
        return searchTypeList;
    }

}
