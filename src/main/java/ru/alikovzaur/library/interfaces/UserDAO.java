package ru.alikovzaur.library.interfaces;

import ru.alikovzaur.library.entityes.AuthInfoEntity;
import ru.alikovzaur.library.entityes.GroupsEntity;
import ru.alikovzaur.library.entityes.SexTabEntity;
import ru.alikovzaur.library.entityes.UsersEntity;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UserDAO {
    List<UsersEntity> getAllUsers();
    UsersEntity getUserByLogin(String login);
    void createUser(UsersEntity usersEntity, GroupsEntity groupsEntity, AuthInfoEntity authInfoEntity) throws Exception;
    GroupsEntity getGroup(String group);
    SexTabEntity getSex(String sex);
}
