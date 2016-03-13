package com.softserve.edu.entity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;
import java.util.UUID;

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
