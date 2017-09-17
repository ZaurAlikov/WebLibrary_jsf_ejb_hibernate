package ru.alikovzaur.library;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class UsersCDI implements Serializable {
    private String login;
    private String password;
    private boolean loginSuccess;
    private boolean createSuccess;

    @Inject
    private UsersController usersController;
//    @Inject
//    private UsersCDI usersCDI;

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
        loginSuccess = usersController.checkPassword(login, password);
        if (loginSuccess){
            return "books";
        }
        this.setLogin("");
        this.setPassword("");
        return "index";
    }

    public void createUser(){
        createSuccess = usersController.createUser(login, password);
    }

}
