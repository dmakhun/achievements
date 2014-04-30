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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Represents bean class for Group entity.
 * 
 * @author Nazar.
 * 
 */
@XmlRootElement
@Entity
@Table(name = "ach_Group")
@NamedQueries({ 
	@NamedQuery(name = Group.SHOW_GROUPS_OPENED_IN_FUTURE, query = Group.SHOW_GROUPS_OPENED_IN_FUTURE_QUERY),
	@NamedQuery(name = Group.SHOW_GROUPS_OPENED_IN_FUTURE_BY_COMPETENCE, query = Group.SHOW_GROUPS_OPENED_IN_FUTURE_BY_COMPETENCE_QUERY),
	@NamedQuery(name = Group.FIND_LIST_GROUPS_BY_COMPETENCE, query = Group.FIND_LIST_GROUPS_BY_COMPETENCE_QUERY),
	@NamedQuery(name = Group.FIND_LIST_GROUPS_BY_COMPETENCE_UUID, query = Group.FIND_LIST_GROUPS_BY_COMPETENCE_UUID_QUERY),
	@NamedQuery(name = Group.ADD_GROUP_TO_USER, query = Group.ADD_GROUP_TO_USER_QUERY),
	@NamedQuery(name = Group.ADD_USER_TO_GROUP, query = Group.ADD_USER_TO_GROUP_QUERY),
	@NamedQuery(name = Group.GET_USER_LIST_IN_GROUP, query = Group.GET_USER_LIST_IN_GROUP_QUERY),
	@NamedQuery(name = Group.FIND_USER_BY_GROUP_UUID, query = Group.FIND_USER_BY_GROUP_UUID_QUERY),
	@NamedQuery(name = Group.GET_GROUP_BY_NAME, query = Group.GET_GROUP_BY_NAME_QUERY),
	@NamedQuery(name=Group.FIND_ONLY_OPENED_GROUPS, query=Group.FIND_ONLY_OPENED_GROUPS_QUERY),
	@NamedQuery(name=Group.FIND_GROUPS, query=Group.FIND_GROUPS_QUERY)
})
public class Group extends AbstractEntity{

	public static final String SHOW_GROUPS_OPENED_IN_FUTURE = "Group.inFuture";
	public static final String SHOW_GROUPS_OPENED_IN_FUTURE_QUERY = "from Group where opened > ?1";
	
	public static final String SHOW_GROUPS_OPENED_IN_FUTURE_BY_COMPETENCE = "Group.inFutureCompetenceId";
	public static final String SHOW_GROUPS_OPENED_IN_FUTURE_BY_COMPETENCE_QUERY = "from Group where opened > ?1 and competence_id = ?2";
	
	public static final String FIND_LIST_GROUPS_BY_COMPETENCE = "Group.findByCompetence";
	public static final String FIND_LIST_GROUPS_BY_COMPETENCE_QUERY = "from Group where competence_id = ?1 ";
	
	public static final String FIND_LIST_GROUPS_BY_COMPETENCE_UUID = "Group.findByCompetenceUuid";
	public static final String FIND_LIST_GROUPS_BY_COMPETENCE_UUID_QUERY = "from Group g INNER JOIN fetch g.competence c WHERE c.uuid = ?1";
	
	public static final String ADD_GROUP_TO_USER = "Group.addUserToUser";
	public static final String ADD_GROUP_TO_USER_QUERY = "from User u WHERE u.uuid = ?1"; 
	
	public static final String ADD_USER_TO_GROUP = "Group.addUserToGroup";
	public static final String ADD_USER_TO_GROUP_QUERY = "from Group g WHERE g.uuid = ?1";
	
	public static final String GET_USER_LIST_IN_GROUP = "Group.userList";
	public static final String GET_USER_LIST_IN_GROUP_QUERY = "select user from User user inner join user.groups ach_group  where ach_group.id = ?1";

	public static final String FIND_USER_BY_GROUP_UUID = "Group.findUsersByGroupUuid";
	public static final String FIND_USER_BY_GROUP_UUID_QUERY = "from User u INNER JOIN fetch u.groups g where g.uuid = ?1";
	
	public static final String GET_GROUP_BY_NAME = "Group.getGroupByName";
	public static final String GET_GROUP_BY_NAME_QUERY = "from Group where name like ?1";
	
	public static final String FIND_ONLY_OPENED_GROUPS = "Group.opened";
	public static final String FIND_ONLY_OPENED_GROUPS_QUERY = "FROM Group g inner join fetch g.competence c WHERE c.id = ?1 and g.closed > ?2";
	
	public static final String FIND_GROUPS = "Group.shit";
	public static final String FIND_GROUPS_QUERY = "FROM Group g inner join fetch g.competence c WHERE c.id = ?1";
	
	/**
	 * @return the group_id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	/**
	 * @return the name
	 */

	@ManyToOne
	@JoinColumn(name = "competence_id")
	private Competence competence;

	@Column(name = "name", length = 50)
	private String name;

	@Column(name = "opened")
	@Temporal(value = TemporalType.DATE)
	private Date opened;

	@Column(name = "closed")
	@Temporal(value = TemporalType.DATE)
	private Date closed;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "ach_UserToGroup", joinColumns = { @JoinColumn(name = "group_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id") })
	private Set<User> users;

	public Group(Competence competence, String name, Date opened, Date closed,
			Set<User> users) {
		super();
		this.competence = competence;
		this.name = name;
		this.opened = opened;
		this.closed = closed;
		this.users = users;
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

	public Date getOpened() {
		return opened;
	}

	public void setOpened(Date opened) {
		this.opened = opened;
	}

	public Date getClosed() {
		return closed;
	}

	public void setClosed(Date closed) {
		this.closed = closed;
	}

	public Group() {
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
		Group other = (Group) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	

}