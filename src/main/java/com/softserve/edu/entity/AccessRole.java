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
        @NamedQuery(name = AccessRole.FIND_ROLE_BY_NAME, query = AccessRole.FIND_ROLE_BY_NAME_QUERY),
        @NamedQuery(name = AccessRole.FIND_USERS_BY_ROLE_UUID, query = AccessRole.FIND_USERS_BY_ROLE_UUID_QUERY)
})
public class AccessRole extends AbstractEntity {

    public static final String FIND_USERS_BY_ROLE_ID = "AccessRole.findUsers";
    public static final String FIND_USERS_BY_ROLE_ID_QUERY = "from User where role_id = ?1";

    public static final String FIND_ROLE_BY_NAME = "AccessRole.findRoleId";
    static final String FIND_ROLE_BY_NAME_QUERY = "from AccessRole where name like ?1";

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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "accessRole")
    private Set<User> user;

    /**
     * Default constructor.
     */
    public AccessRole() {

    }

    /**
     * Parameterized constructor.
     *
     * @param name value for name field
     */

    public AccessRole(String name) {
        this.name = name;
    }

    /**
     * Gets value of id field.
     *
     * @return id
     */

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
