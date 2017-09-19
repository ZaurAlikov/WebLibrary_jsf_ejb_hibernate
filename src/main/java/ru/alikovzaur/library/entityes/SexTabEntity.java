//package ru.alikovzaur.library.entityes;
//
//import org.apache.commons.lang3.builder.EqualsBuilder;
//import org.apache.commons.lang3.builder.HashCodeBuilder;
//
//import javax.persistence.*;
//import java.util.Set;
//
//@Entity
//@Table(name = "sex_tab", schema = "library")
//public class SexTabEntity {
//    private int id;
//    private String sex;
//    private Set<UsersEntity> usersEntities;
//
//    @Id
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    @Basic
//    public String getSex() {
//        return sex;
//    }
//
//    public void setSex(String sex) {
//        this.sex = sex;
//    }
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sex")
//    public Set<UsersEntity> getUsersEntities() {
//        return usersEntities;
//    }
//
//    public void setUsersEntities(Set<UsersEntity> usersEntities) {
//        this.usersEntities = usersEntities;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        return EqualsBuilder.reflectionEquals(this, obj);
//    }
//
//    @Override
//    public int hashCode() {
//        return HashCodeBuilder.reflectionHashCode(this);
//    }
//}
