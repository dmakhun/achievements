package com.edu.academy.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Mapped schedule to one line from CSV.
 */
public class Schedule {

    /**
     * text before first separate sign "," .
     */
    private String subject;
    /**
     * text before second separate sign "," .
     */
    private String startDateStr;
    /**
     * text before third separate sign "," .
     */
    private String startTime;
    /**
     * text before fourth separate sign ","
     */
    private String endDateStr;
    /**
     * text before fifth separate sign "," .
     */
    private String endTime;

    /**
     * text before sixth separate sign "," .
     */
    private String description;
    /**
     * text before seventh separate sign "," .
     */
    private String location;
    /**
     * parse from param subject.
     */
    private String group;
    /**
     * parse from param subject.
     */
    private String meetType;
    /**
     * include startDateStr and startTime.
     */
    private Calendar startDateAndTime = Calendar.getInstance();
    /**
     * include finishDateStr and finishTime.
     */
    private Calendar endDateAndTime = Calendar.getInstance();

    /**
     * @return group.
     */
    public String getGroup() {
        int lastIndex = subject.indexOf("]");
        group = subject.substring(1, lastIndex);
        return group;

    }

    /**
     * @param group .
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * @param startDateStr .
     */
    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    /**
     * @param endDateStr .
     */
    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    /**
     * @param startTime .
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @param endTime .
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * @return description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description .
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location .
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return meetType.
     */
    public String getMeetType() {
        int lastIndex = subject.indexOf("]") + 2;
        meetType = subject.substring(lastIndex);
        return meetType;

    }

    /**
     * @param subject .
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return startDateAndTime.
     */
    public Calendar getStartDateAndTime() {

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy" + "hh:mm a");
        try {
            startDateAndTime.setTime(sdf.parse(startDateStr + startTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return startDateAndTime;

    }

    /**
     * @param startDate .
     */
    public void setStartDateAndTime(Calendar startDate) {
        startDateAndTime = startDate;
    }

    /**
     * @return endDateAndTime.
     */
    public Calendar getEndDateAndTime() {

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy" + "hh:mm a");
        try {
            endDateAndTime.setTime(sdf.parse(endDateStr + endTime));
        } catch (ParseException e) {

            e.printStackTrace();
        }
        return endDateAndTime;
    }

    /**
     * @param endDate .
     */
    public void setEndDateAndTime(Calendar endDate) {
        endDateAndTime = endDate;
    }

}
