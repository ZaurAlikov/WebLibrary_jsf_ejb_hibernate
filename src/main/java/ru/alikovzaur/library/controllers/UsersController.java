package ru.alikovzaur.library.controllers;

import ru.alikovzaur.library.entityes.SexTabEntity;
import ru.alikovzaur.library.entityes.UsersEntity;

import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.SystemEventListener;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.io.Serializable;
import java.sql.Date;
import java.util.ResourceBundle;

@Named
@SessionScoped
public class UsersController implements Serializable {
    private String name;
    private String surname;
    private Date birthday;
    private String email;
    private String sex;
    private String login;
    private String password;
    private boolean loggedIn = false;

    @PersistenceContext(unitName = "libraryPU")
    private EntityManager entityManager;
    @Resource
    private UserTransaction userTransaction;
    @Inject
    private DateController dateController;

    private ResourceBundle res = ResourceBundle.getBundle("nls/message", FacesContext.getCurrentInstance().getViewRoot().getLocale());

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String checkPassword(){
        UsersEntity usersEntity = entityManager.find(UsersEntity.class, login);

        if (usersEntity == null){
            this.setLogin("");
            this.setPassword("");
            FacesMessage message = new FacesMessage(res.getString("error_incorrect_login"));
            FacesContext.getCurrentInstance().addMessage(null, message);
            return "index";
        } else if(password.equals(usersEntity.getPassword())){
            name = usersEntity.getName();
            surname = usersEntity.getSurname();
            birthday = usersEntity.getBirthday();
            email = usersEntity.getEmail();
            sex = usersEntity.getSex().getSex();
            loggedIn = true;
            return "books";
        }
        this.setPassword("");

        FacesMessage message = new FacesMessage(res.getString("error_incorrect_password"));
        FacesContext.getCurrentInstance().addMessage(null, message);
        return "index";
    }

    public String createUser() throws Exception{
        UsersEntity usersEntity = entityManager.find(UsersEntity.class, login);
        if(usersEntity != null){
            this.setLogin("");
            this.setPassword("");
            FacesMessage message = new FacesMessage(res.getString("error_login_exists"));
            FacesContext.getCurrentInstance().addMessage(null, message);
            return "index";
        }

        usersEntity = new UsersEntity();
        usersEntity.setName(name);
        usersEntity.setSurname(surname);
        usersEntity.setBirthday(dateController.getCurrentDate());
        usersEntity.setEmail(email);
        usersEntity.setLogin(login);
        usersEntity.setPassword(password);
        if(sex.equals(res.getString("sex_menu1"))){
            SexTabEntity sexTabEntity = entityManager.find(SexTabEntity.class, 1);
            usersEntity.setSex(sexTabEntity);
        } else if(sex.equals(res.getString("sex_menu2"))){
            SexTabEntity sexTabEntity = entityManager.find(SexTabEntity.class, 2);
            usersEntity.setSex(sexTabEntity);
        }
        userTransaction.begin();
        entityManager.persist(usersEntity);
        userTransaction.commit();
        loggedIn = true;
        return "books";
    }

    public String exit(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "exit";
    }

    public void regListener(ActionEvent event){
        setLogin("");
        setPassword("");
    }

    public void checkLogin(){
        if (!loggedIn){
            FacesContext context = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler handler = (ConfigurableNavigationHandler) context.getApplication().getNavigationHandler();
            handler.performNavigation("/faces/index.xhtml?faces-redirect=true");
        }
    }

}
