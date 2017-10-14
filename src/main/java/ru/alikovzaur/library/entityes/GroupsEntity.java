package ru.alikovzaur.library.entityes;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "groups", schema = "library")
public class GroupsEntity {
    private int id;
    private String group;
    private List<AuthInfoEntity> auth_info;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "group")
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @OneToMany(mappedBy = "group")
    public List<AuthInfoEntity> getAuth_info() {
        return auth_info;
    }

    public void setAuth_info(List<AuthInfoEntity> auth_info) {
        this.auth_info = auth_info;
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
