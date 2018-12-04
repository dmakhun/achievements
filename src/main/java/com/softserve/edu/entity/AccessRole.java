package com.softserve.edu.entity;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "AccessRole")
@NamedQueries({
        @NamedQuery(name = AccessRole.FIND_USERS_BY_ROLE_ID, query = AccessRole.FIND_USERS_BY_ROLE_ID_QUERY),
        @NamedQuery(name = AccessRole.FIND_ROLE_BY_NAME, query = AccessRole.FIND_ROLE_BY_NAME_QUERY)
})
public class AccessRole extends AbstractEntity {

    public static final String FIND_USERS_BY_ROLE_ID = "AccessRole.findUsers";
    public static final String FIND_USERS_BY_ROLE_ID_QUERY = "from User where role_id = ?1";

    public static final String FIND_ROLE_BY_NAME = "AccessRole.findRoleId";
    static final String FIND_ROLE_BY_NAME_QUERY = "from AccessRole where name like ?1";

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", length = 50)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "accessRole")
    private Set<User> user;

    public AccessRole() {
    }

    public AccessRole(String name) {
        this.name = name;
    }

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

}
