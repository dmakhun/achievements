package com.softserve.edu.entity;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "achievements")
public class Achievement extends BaseEntity {

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
        this.achievementType = achievementType;
        this.created = created;
        this.comment = comment;

    }

    public Achievement(AchievementType achievementType, Date created,
            String comment, User user) {
        this.achievementType = achievementType;
        this.created = created;
        this.comment = comment;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
