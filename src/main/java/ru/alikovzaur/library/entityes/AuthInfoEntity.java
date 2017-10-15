package ru.alikovzaur.library.entityes;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "auth_info", schema = "library")
public class AuthInfoEntity {

    private String username;
    private String password;
    private List<GroupsEntity> group;
    @Transient
    private UsersEntity userInfo;

    @Id
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "passwd")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToMany
    @JoinColumn(name = "username", referencedColumnName = "username")
    public List<GroupsEntity> getGroup() {
        return group;
    }

    public void setGroup(List<GroupsEntity> group) {
        this.group = group;
    }

    @OneToOne(mappedBy = "authInfo", cascade = CascadeType.ALL)
    public UsersEntity getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UsersEntity userInfo) {
        this.userInfo = userInfo;
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
