package com.softserve.edu.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class Listener {
	@PrePersist
	public void setUUID(AbstractEntity entity) {
		entity.setUuid(UUID.randomUUID().toString());
		entity.setLastModifiedDate(new Date());
		
	}

	@PreUpdate
	public void setLastModifiedDate(AbstractEntity entity) {
		entity.setLastModifiedDate(new Date());
	}	
}
