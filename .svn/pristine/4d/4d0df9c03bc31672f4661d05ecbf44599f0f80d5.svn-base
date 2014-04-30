package com.softserve.edu.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.softserve.edu.entity.Achievement;
import com.softserve.edu.entity.AchievementType;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.Group;
import com.softserve.edu.entity.Role;
import com.softserve.edu.entity.User;

@XmlRootElement(name = "List")
@XmlSeeAlso({  User.class, Achievement.class, AchievementType.class,
	Competence.class, Group.class, Role.class })
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
