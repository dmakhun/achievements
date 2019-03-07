package com.softserve.edu.entity;

import java.time.LocalDate;
import java.util.Objects;
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

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "dateCreated")
    private LocalDate dateCreated;

    @ManyToMany(mappedBy = "competences", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,
            CascadeType.MERGE})
    private Set<User> users;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "competence")
    private Set<Group> groups;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "competence")
    private Set<AchievementType> achievementTypes;

    public Competence() {
    }

    public Competence(String name, LocalDate dateCreated) {
        this.name = name;
        this.dateCreated = dateCreated;
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

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate date) {
        this.dateCreated = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Competence that = (Competence) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
