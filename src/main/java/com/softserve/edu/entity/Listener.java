package com.softserve.edu.entity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class Listener {

    @PrePersist
    @PreUpdate
    public void setLastModifiedDate(BaseEntity entity) {
        entity.setLastModifiedDate(LocalDateTime.now());
    }
}
