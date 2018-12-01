package com.softserve.edu.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "ach_Competence")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = Competence.FIND_GROUPS_BY_COMPETENCE_ID, query = Competence.FIND_GROUPS_BY_COMPETENCE_ID_QUERY),
        @NamedQuery(name = Competence.FIND_GROUPS_BY_COMPETENCE_UUID, query = Competence.FIND_GROUPS_BY_COMPETENCE_UUID_QUERY),
        @NamedQuery(name = Competence.FIND_COMPETENCE_BY_NAME, query = Competence.FIND_COMPETENCE_BY_NAME_QUERY),
})
public class Competence extends AbstractEntity {

    public static final String FIND_GROUPS_BY_COMPETENCE_ID = "Competence.findGroupsByCompetenceId";
    public static final String FIND_GROUPS_BY_COMPETENCE_ID_QUERY = "from Group where competence_id = ?1";

    public static final String FIND_GROUPS_BY_COMPETENCE_UUID = "Competence.findGroupsByCompetenceId";
    public static final String FIND_GROUPS_BY_COMPETENCE_UUID_QUERY = "FROM Group g INNER JOIN fetch g.competence c WHERE c.uuid like ?1";

    public static final String FIND_COMPETENCE_BY_NAME = "Competence.findByName";
    public static final String FIND_COMPETENCE_BY_NAME_QUERY = "from Competence where name like ?1";

    /**
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * @return the name
     */
    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "created")
    private Date date;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "ach_UserToCompetence", joinColumns = {@JoinColumn(name = "competence_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
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

    @XmlTransient
    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    @XmlTransient
    public Set<AchievementType> getAchievementTypes() {
        return achievementTypes;
    }

    public void setAchievementTypes(Set<AchievementType> achievementTypes) {
        this.achievementTypes = achievementTypes;
    }

    @XmlTransient
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @XmlTransient
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Competence other = (Competence) obj;
        if (id == null) {
            return other.id == null;
        } else return id.equals(other.id);
    }


}
