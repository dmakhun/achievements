package com.edu.academy.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "groups")
public class Group extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "competence_id")
    private Competence competence;

    @Column(name = "name", length = 50)
    private String name;

    private LocalDate dateOpened;

    private LocalDate dateClosed;

    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,
            CascadeType.MERGE})
    private Set<User> users;

    public Group(Competence competence, String name, LocalDate dateOpened, LocalDate dateClosed,
                 Set<User> users) {
        this.competence = competence;
        this.name = name;
        this.dateOpened = dateOpened;
        this.dateClosed = dateClosed;
        this.users = users;
    }

    public Group() {

    }

    public Set<User> getUsers() {
        return users != null ? users : new HashSet<>();
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }


    public Competence getCompetence() {
        return competence;
    }

    public void setCompetence(Competence competence) {
        this.competence = competence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(LocalDate opened) {
        dateOpened = opened;
    }

    public LocalDate getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(LocalDate closed) {
        dateClosed = closed;
    }


}