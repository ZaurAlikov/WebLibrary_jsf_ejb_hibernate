package ru.alikovzaur.library.entityes;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Entity
@Table(name = "auth_info", schema = "library")
public class AuthInfoEntity {
    private int id;
    private String login;
    private String password;
    private GroupsEntity group;
    @Transient
    private UsersEntity userInfo;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToOne
    @JoinColumn(name = "group_id")
    public GroupsEntity getGroup() {
        return group;
    }

    @OneToOne(mappedBy = "authInfo", cascade = CascadeType.ALL)
    public UsersEntity getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UsersEntity userInfo) {
        this.userInfo = userInfo;
    }

    public void setGroup(GroupsEntity group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
