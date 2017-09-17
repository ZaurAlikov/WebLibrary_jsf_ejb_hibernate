package ru.alikovzaur.library;

import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.io.Serializable;

@Named
@SessionScoped
public class UsersController implements Serializable {
    private String login;
    private String password;

    @PersistenceContext(unitName = "libraryPU")
    private EntityManager entityManager;
    @Resource
    private UserTransaction userTransaction;

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
            return "index";
        } else if(password.equals(usersEntity.getPassword())){
            return "books";
        }
        this.setLogin("");
        this.setPassword("");
        return "index";
    }

    public String createUser() throws Exception{
        UsersEntity usersEntity = entityManager.find(UsersEntity.class, login);
        if(usersEntity != null){
            this.setLogin("");
            this.setPassword("");
            return "index";
        }

        usersEntity = new UsersEntity();
        usersEntity.setLogin(login);
        usersEntity.setPassword(password);
        userTransaction.begin();
        entityManager.persist(usersEntity);
        userTransaction.commit();
        return "books";
    }
}
