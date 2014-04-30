package com.softserve.edu.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Represents bean class for Competence entity.
 * 
 * @author MyronKurus.
 * 
 */
@XmlRootElement
@Entity
@Table(name = "ach_Achievement")
@NamedQueries({ 
	@NamedQuery(name = Achievement.GET_ACHIEVEMENT, query = Achievement.GET_ACHIEVEMENT_QUERY),
	@NamedQuery(name = Achievement.GET_ACHIEVEMENT_FROM_USER, query = Achievement.GET_ACHIEVEMENT_FROM_USER_QUERY),
	@NamedQuery(name = Achievement.GET_ACHIEVEMENT_FROM_LIST_ACHIEVEMENT, query = Achievement.GET_ACHIEVEMENT_FROM_LIST_ACHIEVEMENT_QUERY)
})
public class Achievement extends AbstractEntity{
	
	public static final String GET_ACHIEVEMENT = "Achievement.findByAchievementTypeAndUser";
	public static final String GET_ACHIEVEMENT_QUERY = "from Achievement where achievement_type_id = ?1 and user_id = ?2";

	public static final String GET_ACHIEVEMENT_FROM_USER = "Achievement.getAchievementsByUserUuidFromUser";
	public static final String GET_ACHIEVEMENT_FROM_USER_QUERY = "from User WHERE uuid like ?1";
	
	public static final String GET_ACHIEVEMENT_FROM_LIST_ACHIEVEMENT = "Achievement.getAchievementsByUserUuidFromAchievement";
	public static final String GET_ACHIEVEMENT_FROM_LIST_ACHIEVEMENT_QUERY = "from Achievement WHERE user_id like ?1";
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "achievement_type_id")
	private AchievementType achievementType;

	@Column(name = "created")
	@Temporal(value = TemporalType.DATE)
	private Date created;

	@Column(name = "comment")
	private String comment;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Achievement() {
	}

	public Achievement(Date created, String comment) {
		this.created = created;
		this.comment = comment;
	}

	public Achievement(AchievementType achievementType, Date created,
			String comment) {
		super();
		this.achievementType = achievementType;
		this.created = created;
		this.comment = comment;
		
	}



	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@XmlTransient
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AchievementType getAchievementType() {
		return achievementType;
	}

	public void setAchievementType(AchievementType achievementType) {
		this.achievementType = achievementType;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
