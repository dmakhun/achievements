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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "groups")
@NamedQueries({
        @NamedQuery(name = Group.FIND_GROUPS_OPENED_IN_FUTURE_BY_COMPETENCE_ID, query = Group.SHOW_GROUPS_OPENED_IN_FUTURE_BY_COMPETENCE_QUERY),
        @NamedQuery(name = Group.GET_GROUP_BY_NAME, query = Group.GET_GROUP_BY_NAME_QUERY),
        @NamedQuery(name = Group.FIND_OPENED_GROUPS, query = Group.FIND_ONLY_OPENED_GROUPS_QUERY),
        @NamedQuery(name = Group.FIND_GROUPS, query = Group.FIND_GROUPS_QUERY)})
public class Group extends AbstractEntity {

    public static final String FIND_GROUPS_OPENED_IN_FUTURE_BY_COMPETENCE_ID = "Group.inFutureCompetenceId";
    public static final String SHOW_GROUPS_OPENED_IN_FUTURE_BY_COMPETENCE_QUERY = "from Group where dateOpened > ?1 and competence_id = ?2";

    public static final String GET_GROUP_BY_NAME = "Group.getGroupByName";
    public static final String GET_GROUP_BY_NAME_QUERY = "from Group where name like ?1";

    public static final String FIND_OPENED_GROUPS = "Group.opened";
    public static final String FIND_ONLY_OPENED_GROUPS_QUERY = "FROM Group g inner join fetch g.competence c WHERE c.id = ?1 and g.dateClosed > ?2";

    public static final String FIND_GROUPS = "Group.all";
    public static final String FIND_GROUPS_QUERY = "FROM Group g inner join fetch g.competence c WHERE c.id = ?1";


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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "UserToGroup", joinColumns = {
            @JoinColumn(name = "group_id")}, inverseJoinColumns = {
            @JoinColumn(name = "user_id")})
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