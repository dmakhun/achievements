package com.softserve.edu.entity;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "achievementtypes")
public class AchievementType extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "competence_id")
    private Competence competence;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "points", length = 50)
    private Integer points;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "achievementType")
    private Set<Achievement> achievement;

    public AchievementType() {
    }

    public AchievementType(Competence competence, String name, Integer points) {
        this.competence = competence;
        this.name = name;
        this.points = points;
    }

    public AchievementType(Competence competence, String name, Integer points,
            Set<Achievement> achievement) {
        this.competence = competence;
        this.name = name;
        this.points = points;
        this.achievement = achievement;
    }


    public Set<Achievement> getAchievement() {
        return achievement;
    }

    public AchievementType setAchievement(Set<Achievement> achievement) {
        this.achievement = achievement;

        return this;
    }

    public Competence getCompetence() {
        return competence;
    }

    public AchievementType setCompetence(Competence competence) {
        this.competence = competence;

        return this;
    }

    public String getName() {
        return name;
    }

    public AchievementType setName(String name) {
        this.name = name;

        return this;
    }

    public Integer getPoints() {
        return points;
    }

    public AchievementType setPoints(Integer points) {
        this.points = points;

        return this;
    }
}
