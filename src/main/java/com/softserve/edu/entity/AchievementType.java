package com.softserve.edu.entity;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "ach_AchievementType")
@NamedQueries({
        @NamedQuery(name = AchievementType.GET_LIST_ACHIEVEMENT_TYPE, query = AchievementType.GET_LIST_ACHIEVEMENT_TYPE_QUERY),
        @NamedQuery(name = AchievementType.GET_ACHIEVEMENT_TYPES_BY_COMPETENCE_UUID, query = AchievementType.GET_ACHIEVEMENT_TYPES_BY_COMPETENCE_UUID_QUERY)
})
public class AchievementType extends AbstractEntity {

    public static final String GET_LIST_ACHIEVEMENT_TYPE = "AchievementType.getList";
    public static final String GET_LIST_ACHIEVEMENT_TYPE_QUERY = "from AchievementType where competence_id = ?1";

    public static final String GET_ACHIEVEMENT_TYPES_BY_COMPETENCE_UUID = "AchievementType.getAchievementTypesByCompetenceUuid";
    public static final String GET_ACHIEVEMENT_TYPES_BY_COMPETENCE_UUID_QUERY = "from AchievementType at INNER JOIN fetch at.competence c WHERE c.uuid = ?1";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((achievement == null) ? 0 : achievement.hashCode());
        result = prime * result
                + ((competence == null) ? 0 : competence.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((points == null) ? 0 : points.hashCode());
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
        AchievementType other = (AchievementType) obj;
        if (achievement == null) {
            if (other.achievement != null) {
                return false;
            }
        } else if (!achievement.equals(other.achievement)) {
            return false;
        }
        if (competence == null) {
            if (other.competence != null) {
                return false;
            }
        } else if (!competence.equals(other.competence)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (points == null) {
            return other.points == null;
        } else {
            return points.equals(other.points);
        }
    }
}
