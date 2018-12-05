package com.softserve.edu.entity;

import com.softserve.edu.util.FieldForSearch;
import com.softserve.edu.validation.ValidEmail;
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
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Entity
@Table(name = "User")
@NamedQueries({
        @NamedQuery(name = User.FIND_USER_BY_USERNAME, query = User.FIND_USER_BY_NAME_QUERY),
        @NamedQuery(name = User.FIND_USER_BY_EMAIL, query = User.FIND_USER_BY_EMAIL_QUERY),
        @NamedQuery(name = User.FIND_ONLY_OPENED_GROUPS, query = User.FIND_ONLY_OPENED_GROUPS_QUERY),
        @NamedQuery(name = User.FIND_ALL_USERS_BY_ROLE, query = User.FIND_ALL_BY_ROLE_QUERY)
})
public class User extends AbstractEntity {

    public static final String FIND_USER_BY_USERNAME = "User.findByUsername";
    public static final String FIND_USER_BY_NAME_QUERY = "FROM User WHERE username LIKE ?1";

    public static final String FIND_USER_BY_EMAIL = "User.findByEmail";
    public static final String FIND_USER_BY_EMAIL_QUERY = "FROM User WHERE email LIKE ?1";

    public static final String FIND_ONLY_OPENED_GROUPS = "User.openedGroups";
    public static final String FIND_ONLY_OPENED_GROUPS_QUERY = "FROM Group g inner join fetch g.users u WHERE u.id = ?1 and g.dateClosed > ?2";

    public static final String FIND_ALL_USERS_BY_ROLE = "User.all";
    public static final String FIND_ALL_BY_ROLE_QUERY = "FROM User u inner join fetch u.accessRole r WHERE r.id = ?1";

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private AccessRole accessRole;

    @Size(min = 2, max = 15)
    @Column(name = "name", length = 255)
    @FieldForSearch(nameForView = "Name")
    private String name;

    @Column(name = "surname", length = 255)
    @FieldForSearch(nameForView = "Surname")
    private String surname;

    @Size(min = 4, max = 25)
    @Pattern(regexp = "^\\w{4,}$")
    @Column(name = "username", length = 25, unique = true)
    @FieldForSearch(nameForView = "Username")
    private String username;

    @Size(min = 4, max = 30)
    @Pattern(regexp = "^\\S+$")
    @Column(name = "password", length = 80)
    private String password;

    @ValidEmail
    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;

    @Lob
    @Column(name = "picture")
    private byte[] picture;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "UserToCompetence", joinColumns = {
            @JoinColumn(name = "user_id")}, inverseJoinColumns = {
            @JoinColumn(name = "competence_id")})
    private Set<Competence> competences;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Achievement> achievements;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "UserToGroup", joinColumns = {
            @JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "group_id")})
    private Set<Group> groups;

    public User() {
    }

    public User(String name, String surname, String username, AccessRole accessRole,
            String password, byte[] picture) {
        this.accessRole = accessRole;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.picture = picture;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccessRole getAccessRole() {
        return accessRole;
    }

    public void setAccessRole(AccessRole accessRole) {
        this.accessRole = accessRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Set<Competence> getCompetences() {
        return competences;
    }

    public void setCompetences(Set<Competence> competences) {
        this.competences = competences;
    }


    public Set<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(Set<Achievement> achievements) {
        this.achievements = achievements;
    }


    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
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
        User other = (User) obj;
        if (id == null) {
            return other.id == null;
        } else {
            return id.equals(other.id);
        }
    }


    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

}
