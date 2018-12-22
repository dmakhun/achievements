package com.softserve.edu.entity;

import javax.persistence.*;
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

    private LocalDate opened;

    private LocalDate closed;

    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,
            CascadeType.MERGE})
    private Set<User> users;

    public Group(Competence competence, String name, LocalDate opened, LocalDate closed,
                 Set<User> users) {
        this.competence = competence;
        this.name = name;
        this.opened = opened;
        this.closed = closed;
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

    public LocalDate getOpened() {
        return opened;
    }

    public void setOpened(LocalDate opened) {
        this.opened = opened;
    }

    public LocalDate getClosed() {
        return closed;
    }

    public void setClosed(LocalDate closed) {
        this.closed = closed;
    }


}