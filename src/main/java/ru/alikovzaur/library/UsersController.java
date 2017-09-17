package ru.alikovzaur.library;

import org.apache.commons.lang3.StringUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

//@Named
//@RequestScoped
public class UsersController implements Serializable {

        @PersistenceContext(unitName = "libraryPU")
        private EntityManager entityManager;

        public boolean checkPassword(String login, String password){
//            if(StringUtils.isEmpty(login) || StringUtils.isEmpty(password)){
//                return false;
//            }

            UsersEntity usersEntity = entityManager.find(UsersEntity.class, login);
            if(usersEntity == null){
                return false;
            }

            if(password.equals(usersEntity.getPassword())){
                return true;
            }

            return false;
        }

        public boolean createUser(String login, String password){
//            if(StringUtils.isEmpty(login) || StringUtils.isEmpty(password)){
//                return false;
//            }

            UsersEntity usersEntity = entityManager.find(UsersEntity.class, login);
            if(usersEntity != null){
                return false;
            }

            usersEntity = new UsersEntity();
            usersEntity.setLogin(login);
            usersEntity.setPassword(password);
            entityManager.persist(usersEntity);

            return true;
        }

        public List<UsersEntity> getAllUsers(){
            Query query = entityManager.createQuery("select entity from UsersEntity entity");
            return query.getResultList();
        }
    }
