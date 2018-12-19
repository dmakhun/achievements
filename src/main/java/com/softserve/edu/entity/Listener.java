package com.softserve.edu.entity;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class Listener {

    @PrePersist
    @PreUpdate
    public void setLastModifiedDate(AbstractEntity entity) {
        entity.setLastModifiedDate(new Date());

    }
}
