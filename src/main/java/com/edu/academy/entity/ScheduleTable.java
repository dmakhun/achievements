package com.edu.academy.entity;

import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "schedules")
public class ScheduleTable extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "meetingType", length = 200)
    private String meetingType;

    @Column(name = "begin_time")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Calendar begin;

    @Column(name = "end_time")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Calendar end;

    @Column(name = "description", length = 200)
    private String description;

    @Column(name = "location", length = 50)
    private String location;

    public ScheduleTable() {
    }

    public ScheduleTable(Group group, String meetingType, Calendar begin,
            Calendar end, String description, String location) {
        this.group = group;
        this.meetingType = meetingType;
        this.begin = begin;
        this.end = end;
        this.description = description;
        this.location = location;
    }

    public Group getaGroup() {
        return group;
    }

    public void setaGroup(Group aGroup) {
        group = aGroup;
    }

    public String getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(String meetingType) {
        this.meetingType = meetingType;
    }

    public Calendar getBegin() {
        return begin;
    }

    public void setBegin(Calendar begin) {
        this.begin = begin;
    }

    public Calendar getEnd() {
        return end;
    }

    public void setEnd(Calendar end) {
        this.end = end;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
