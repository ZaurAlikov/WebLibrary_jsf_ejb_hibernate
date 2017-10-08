package ru.alikovzaur.library.controllers;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Locale;

@Named
@SessionScoped
public class LocaleChanger implements Serializable {
    private static Locale LOCALE_RU = new Locale("ru_RU");
    private static Locale LOCALE_EN = new Locale("en_US");
    private Locale locale = LOCALE_RU;

    public Locale getLocale() {
        return locale;
    }

    public String selectLanguage(String selectedLanguage) {
        locale = convert(selectedLanguage);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
        return FacesContext.getCurrentInstance().getViewRoot().getViewId() + "?faces-redirect=true";//перезагружаем страницу
    }

    private static Locale convert(String languageName) {
        if ("en".equals(languageName)) {
            return LOCALE_EN;
        }
        return LOCALE_RU;
    }
}

