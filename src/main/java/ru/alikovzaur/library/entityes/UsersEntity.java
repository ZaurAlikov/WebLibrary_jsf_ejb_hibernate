package ru.alikovzaur.library.entityes;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "users", schema = "library")
public class UsersEntity {
    private int id;
    private String name;
    private String surname;
    private Date birthday;
    private String email;
    private SexTabEntity sex;
    private AuthInfoEntity authInfo;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @JoinColumn(name = "auth_id")
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
