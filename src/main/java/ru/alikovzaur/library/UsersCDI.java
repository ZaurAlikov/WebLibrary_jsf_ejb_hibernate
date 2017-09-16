package ru.alikovzaur.library;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class UsersCDI implements Serializable {
    private String login;
    private String password;
    private boolean loginSuccess;
    private boolean createSuccess;

    @EJB
    private UsersEJB usersEJB;

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

    public void checkPassword(){
        loginSuccess = usersEJB.checkPassword(login, password);
    }

    public void createUser(){
        createSuccess = usersEJB.createUser(login, password);
    }

}
