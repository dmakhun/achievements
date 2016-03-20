package com.softserve.edu.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Set;

/**
 * Represents bean class for Roles entity. Mapped on table roles.
 *
 * @author Edgar
 */
@XmlRootElement
@Entity
@Table(name = "ach_Role")
@NamedQueries({
        @NamedQuery(name = Role.FIND_USERS_BY_ROLE_ID, query = Role.FIND_USERS_BY_ROLE_ID_QUERY),
        @NamedQuery(name = Role.FIND_ROLE_BY_NAME, query = Role.FIND_ROLE_BY_NAME_QUERY),
        @NamedQuery(name = Role.FIND_ROLE_BY_ROLE_NAME, query = Role.FIND_ROLE_BY_ROLE_NAME_QUERY),
        @NamedQuery(name = Role.FIND_USERS_BY_ROLE_UUID, query = Role.FIND_USERS_BY_ROLE_UUID_QUERY)
})
public class Role extends AbstractEntity {

    public static final String FIND_USERS_BY_ROLE_ID = "Role.findUsers";
    public static final String FIND_USERS_BY_ROLE_ID_QUERY = "from User where role_id = ?1";

    public static final String FIND_ROLE_BY_NAME = "Role.findRole";
    public static final String FIND_ROLE_BY_NAME_QUERY = "from Role where name like ?1";

    public static final String FIND_ROLE_BY_ROLE_NAME = "Role.findRoleByRolename";
    public static final String FIND_ROLE_BY_ROLE_NAME_QUERY = "from Role where name like ?1";

    public static final String FIND_USERS_BY_ROLE_UUID = "ROLE";
    public static final String FIND_USERS_BY_ROLE_UUID_QUERY = "from User u INNER JOIN fetch u.role r WHERE r.uuid	like ?1";


    /**
     * Id field. It's PK and must be generated value. Mapped on column id.
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * name field. Mapped on column name.
     */
    @Column(name = "name", length = 50)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "role")
    private Set<User> user;

    /**
     * Default constructor.
     */
    public Role() {

    }

    /**
     * Parameterized constructor.
     *
     * @param name value for name field
     */

    public Role(String name) {
        this.name = name;
    }

    /**
     * Gets value of id field.
     *
     * @return id
     */
    @XmlTransient
    public Long getId() {
        return id;
    }

    /**
     * Sets setId field.
     *
     * @param id value for id field
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets value of id name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets setName field.
     *
     * @param name value for name field
     */
    public void setName(String name) {
        this.name = name;
    }

}
