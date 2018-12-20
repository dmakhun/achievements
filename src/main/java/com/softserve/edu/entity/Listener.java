package com.softserve.edu.entity;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class Listener {

    @PrePersist
    @PreUpdate
    public void setLastModifiedDate(BaseEntity entity) {
        entity.setLastModifiedDate(new Date());

    }
}
