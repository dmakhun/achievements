package com.softserve.edu.entity;

import static java.util.Arrays.asList;

import com.softserve.edu.util.FieldForSearch;
import com.softserve.edu.validation.ValidEmail;
import java.util.HashSet;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Entity
@Table(name = "User")
public class User extends AbstractEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

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

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "UserToCompetence", joinColumns = {
            @JoinColumn(name = "user_id")}, inverseJoinColumns = {
            @JoinColumn(name = "competence_id")})
    private Set<Competence> competences;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Achievement> achievements;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "UserToGroup", joinColumns = {
            @JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "group_id")})
    private Set<Group> groups;

    public User() {
    }

    public User(String name, String surname, String username, Role role,
            String password, byte[] picture) {
        this.role = role;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
        for (Competence competence : competences) {
            competence.getUsers().add(this);
            this.competences = competences;
        }
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
        for (Group group : groups) {
            group.getUsers().add(this);
            this.groups = groups;
        }
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

    public void addGroup(Group group) {
        setGroups(new HashSet<>(asList(group)));
        group.getUsers().add(this);
    }

    public void addCompetence(Competence competence) {
        setCompetences(new HashSet<>(asList(competence)));
        competence.getUsers().add(this);
    }

    public void removeCompetence(Competence competence) {
        getCompetences().remove(competence);
        competence.getUsers().remove(this);
    }

}
