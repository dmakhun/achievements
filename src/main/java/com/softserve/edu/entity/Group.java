package com.softserve.edu.entity;

import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "groups")
public class Group extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Group other = (Group) obj;
        if (id == null) {
            return other.id == null;
        } else {
            return id.equals(other.id);
        }
    }

}