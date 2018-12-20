package com.softserve.edu.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "groups")
public class Group extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "competence_id")
    private Competence competence;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "opened")
    @Temporal(value = TemporalType.DATE)
    private Date dateOpened;

    @Column(name = "closed")
    @Temporal(value = TemporalType.DATE)
    private Date dateClosed;

    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,
            CascadeType.MERGE})
    private Set<User> users;

    public Group(Competence competence, String name, Date dateOpened, Date dateClosed,
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

    public Date getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(Date opened) {
        dateOpened = opened;
    }

    public Date getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(Date closed) {
        dateClosed = closed;
    }


}