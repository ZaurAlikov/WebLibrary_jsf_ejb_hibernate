package ru.alikovzaur.library.entityes;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "users", schema = "library")
public class UsersEntity {
    private String username;
    private String name;
    private String surname;
    private Date birthday;
    private String email;
    private SexTabEntity sex;
    private AuthInfoEntity authInfo;

    @Id
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Basic
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ManyToOne
    @JoinColumn(name = "sex_id", nullable = false)
    public SexTabEntity getSex() {
        return this.sex;
    }

    public void setSex(SexTabEntity sex) {
        this.sex = sex;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username")
    public AuthInfoEntity getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(AuthInfoEntity authInfo) {
        this.authInfo = authInfo;
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
