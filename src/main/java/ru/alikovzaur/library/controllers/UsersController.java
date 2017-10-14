package ru.alikovzaur.library.controllers;

import ru.alikovzaur.library.entityes.AuthInfoEntity;
import ru.alikovzaur.library.entityes.UsersEntity;
import ru.alikovzaur.library.interfaces.UserDAO;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Date;
import java.util.ResourceBundle;

@Named
@SessionScoped
public class UsersController implements Serializable {
    private int id;
    private String name;
    private String surname;
    private Date birthday;
    private String email;
    private String sex;
    private String login;
    private String password;
    private String group;
    private boolean loggedIn = false;

    @EJB
    private UserDAO userDao;

    @Inject
    private DateController dateController;

    private ResourceBundle res = ResourceBundle.getBundle("nls/message", FacesContext.getCurrentInstance().getViewRoot().getLocale());

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String checkPassword(){
        UsersEntity usersEntity = userDao.getUserByLogin(login);
        if(usersEntity == null){
            this.setLogin("");
            this.setPassword("");
            setErrorMessage("error_incorrect_login");
            return "index";
        } else if (usersEntity.getAuthInfo().getPassword().equals(password)){
            id = usersEntity.getId();
            name = usersEntity.getName();
            surname = usersEntity.getSurname();
            birthday = usersEntity.getBirthday();
            email = usersEntity.getEmail();
            sex = usersEntity.getSex().getSex();
            group = usersEntity.getAuthInfo().getGroup().getGroup();
            loggedIn = true;
            return "books";
        }
        this.setPassword("");
        setErrorMessage("error_incorrect_password");
        return "index";
    }

    @SuppressWarnings("unchecked")
    public String createUser() throws Exception{
        UsersEntity usersEntity = userDao.getUserByLogin(login);
        if(usersEntity != null){
            this.setLogin("");
            this.setPassword("");
            setErrorMessage("error_login_exists");
            return "registration";
        }
        usersEntity = new UsersEntity();
        usersEntity.setName(name);
        usersEntity.setSurname(surname);
        usersEntity.setBirthday(dateController.getCurrentDate());
        usersEntity.setEmail(email);
        usersEntity.setSex(userDao.getSex(sex));
        AuthInfoEntity authInfoEntity = new AuthInfoEntity();
        authInfoEntity.setLogin(login);
        authInfoEntity.setPassword(password);
        authInfoEntity.setGroup(userDao.getGroup("users"));
        usersEntity.setAuthInfo(authInfoEntity);
        userDao.createUser(usersEntity);
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

    private void setErrorMessage(String msg){
        FacesMessage message = new FacesMessage(res.getString(msg));
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}