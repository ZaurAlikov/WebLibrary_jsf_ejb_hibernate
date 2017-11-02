package ru.alikovzaur.library.controllers;

import ru.alikovzaur.library.entityes.AuthInfoEntity;
import ru.alikovzaur.library.entityes.GroupsEntity;
import ru.alikovzaur.library.entityes.UsersEntity;
import ru.alikovzaur.library.interfaces.UserDAO;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static ru.alikovzaur.library.utils.Sha256Converter.hash256;

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
    private List<GroupsEntity> group;
    private UIComponent loginField;

    @EJB
    private UserDAO userDao;

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

    public List<GroupsEntity> getGroup() {
        return group;
    }

    public void setGroup(List<GroupsEntity> group) {
        this.group = group;
    }

    public UIComponent getLoginField() {
        return loginField;
    }

    public void setLoginField(UIComponent loginField) {
        this.loginField = loginField;
    }

    public String loginEnter(){
        HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        try {
            String username = httpServletRequest.getRemoteUser();
            if (username != null) {
                httpServletRequest.logout();
            }
            httpServletRequest.login(login,password);
            UsersEntity usersEntity = userDao.getUserByLogin(login);
            name = usersEntity.getName();
            surname = usersEntity.getSurname();
            birthday = usersEntity.getBirthday();
            email = usersEntity.getEmail();
            sex = usersEntity.getSex().getSex();
            group = usersEntity.getAuthInfo().getGroup();
            return "books";
        } catch (ServletException e) {
            setErrorMessage("error_login_failed");
        }
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

        GroupsEntity groupsEntity = new GroupsEntity();
        List<GroupsEntity> groupsEntities = new ArrayList<>();
        groupsEntity.setUsername(login);
        groupsEntity.setRole("users");
        groupsEntities.add(groupsEntity);

        AuthInfoEntity authInfoEntity = new AuthInfoEntity();
        authInfoEntity.setUsername(login);
        authInfoEntity.setPassword(hash256(password));
        authInfoEntity.setGroup(groupsEntities);

        usersEntity = new UsersEntity();
        usersEntity.setUsername(login);
        usersEntity.setName(name);
        usersEntity.setSurname(surname);
        usersEntity.setBirthday(dateController.getCurrentDate());
        usersEntity.setEmail(email);
        usersEntity.setSex(userDao.getSex(sex));
        usersEntity.setAuthInfo(authInfoEntity);

        userDao.createUser(usersEntity, groupsEntity, authInfoEntity);
        return "index";
    }

    public String exit() throws ServletException{
        HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        httpServletRequest.logout();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "exit";
    }

    public void regListener(ActionEvent event){
        setLogin("");
        setPassword("");
    }

    public String regCancel(){
        return "index?faces-redirect=true";
    }

    private void setErrorMessage(String msg){
        FacesMessage message = new FacesMessage(res.getString(msg));
        FacesContext context = FacesContext.getCurrentInstance();
        if (loginField.getId().equals("login")){
            context.addMessage(loginField.getClientId(context), message);
        } else {
            context.addMessage(null, message);
        }
    }

    public void listener(ValueChangeEvent e){
        loginField = (UIComponent) e.getSource();
    }

}