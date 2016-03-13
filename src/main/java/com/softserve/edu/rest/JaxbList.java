package com.softserve.edu.rest;

import com.softserve.edu.entity.*;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "List")
@XmlSeeAlso({User.class, Achievement.class, AchievementType.class,
        Competence.class, Group.class, Role.class})
@XmlAccessorType(XmlAccessType.NONE)
class JaxbList<T> {
    private List<T> list;

    public JaxbList() {
    }

    public JaxbList(List<T> list) {
        this.list = list;
    }

    @XmlElement(name = "Item")
    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
