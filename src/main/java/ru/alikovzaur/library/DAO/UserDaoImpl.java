package ru.alikovzaur.library.DAO;

import ru.alikovzaur.library.entityes.AuthInfoEntity;
import ru.alikovzaur.library.entityes.GroupsEntity;
import ru.alikovzaur.library.entityes.SexTabEntity;
import ru.alikovzaur.library.entityes.UsersEntity;
import ru.alikovzaur.library.interfaces.UserDAO;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.ResourceBundle;

@Stateless
public class UserDaoImpl implements UserDAO {

    @PersistenceContext(unitName = "libraryPU")
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<UsersEntity> getAllUsers() {
        Query query = entityManager.createQuery("select user from UsersEntity user join fetch user.sex join fetch user.authInfo");
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public UsersEntity getUserByLogin(String login) {
        Query query = entityManager.createQuery("select user from UsersEntity user join fetch user.sex join fetch user.authInfo where user.username = :login");
        query.setParameter("login", login);
        List<UsersEntity> usersEntities = query.getResultList();
        if (usersEntities.size() == 0){
            return null;
        }
        return usersEntities.get(0);
    }

    @Override
    public void createUser(UsersEntity usersEntity, GroupsEntity groupsEntity, AuthInfoEntity authInfoEntity) throws Exception {
        entityManager.persist(authInfoEntity);
        entityManager.persist(groupsEntity);
        entityManager.persist(usersEntity);
    }

//    @Override
//    public GroupsEntity getGroup(String group) {
//        GroupsEntity groupsEntity = null;
//        if (group.equals("admins")){
//            groupsEntity = entityManager.find(GroupsEntity.class, 1);
//        } else if (group.equals("users")){
//            groupsEntity = entityManager.find(GroupsEntity.class, 2);
//        }
//        return groupsEntity;
//    }

    @Override
    public SexTabEntity getSex(String sex) {
        ResourceBundle res = ResourceBundle.getBundle("nls/message", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        SexTabEntity sexTabEntity = null;
        if (sex.equals(res.getString("sex_menu1"))){
            sexTabEntity = entityManager.find(SexTabEntity.class, 1);
        } else if (sex.equals(res.getString("sex_menu2"))){
            sexTabEntity = entityManager.find(SexTabEntity.class, 2);
        }
        return sexTabEntity;
    }
}
