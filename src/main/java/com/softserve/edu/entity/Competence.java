package com.softserve.edu.entity;

import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "competences")
public class Competence extends BaseEntity {

    /**
     * @return the name
     */
    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "created")
    private Date date;

    @ManyToMany(mappedBy = "competences", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,
            CascadeType.MERGE})
    private Set<User> users;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "competence")
    private Set<Group> groups;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "competence")
    private Set<AchievementType> achievementTypes;

    public Competence() {
    }

    public Competence(String name, Date date) {
        this.name = name;
        this.date = date;
    }

    public Competence(String name) {
        this.name = name;
    }


    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> aGroups) {
        groups = aGroups;
    }


    public Set<AchievementType> getAchievementTypes() {
        return achievementTypes;
    }

    public void setAchievementTypes(Set<AchievementType> achievementTypes) {
        this.achievementTypes = achievementTypes;
    }


    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
