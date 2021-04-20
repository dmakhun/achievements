package com.edu.academy.entity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class EntityListener {

    @PrePersist
    @PreUpdate
    public void setLastModifiedDate(BaseEntity entity) {
        entity.setLastModifiedDate(LocalDateTime.now());
    }
}
