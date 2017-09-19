package ru.alikovzaur.library.controllers;

//import ru.alikovzaur.library.entityes.SexTabEntity;
import ru.alikovzaur.library.entityes.UsersEntity;

import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.io.Serializable;
import java.sql.Date;

@Named
@SessionScoped
public class UsersController implements Serializable {
    private String name;
    private String surname;
    private Date birthday;
    private String email;
//    private SexTabEntity sex;
    private String login;
    private String password;

    @PersistenceContext(unitName = "libraryPU")
    private EntityManager entityManager;
    @Resource
    private UserTransaction userTransaction;

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

    //    public SexTabEntity getSex() {
//        return sex;
//    }
//
//    public void setSex(SexTabEntity sex) {
//        this.sex = sex;
//    }

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

    public String checkPassword(){
        UsersEntity usersEntity = entityManager.find(UsersEntity.class, login);

        if (usersEntity == null){
            this.setLogin("");
            this.setPassword("");
            FacesMessage message = new FacesMessage("Пользователя с таким именем не существует");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return "index";
        } else if(password.equals(usersEntity.getPassword())){
            return "books";
        }
        this.setPassword("");
        FacesMessage message = new FacesMessage("Неверный пароль");
        FacesContext.getCurrentInstance().addMessage(null, message);
        return "index";
    }

    public String createUser() throws Exception{
        UsersEntity usersEntity = entityManager.find(UsersEntity.class, login);
        if(usersEntity != null){
            this.setLogin("");
            this.setPassword("");
            FacesMessage message = new FacesMessage("Пользователь с таким именем уже существует");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return "index";
        }

        usersEntity = new UsersEntity();
        usersEntity.setName(name);
        usersEntity.setSurname(surname);
//        usersEntity.setBirthday(birthday);
        usersEntity.setEmail(email);
        usersEntity.setLogin(login);
        usersEntity.setPassword(password);
        userTransaction.begin();
        entityManager.persist(usersEntity);
        userTransaction.commit();
        return "books";
    }
}
