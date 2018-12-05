package com.softserve.edu.entity;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class Listener {

    @PrePersist
    public void setLastModifiedDatePrePersist(AbstractEntity entity) {
        entity.setLastModifiedDate(new Date());

    }

    @PreUpdate
    public void setLastModifiedDate(AbstractEntity entity) {
        entity.setLastModifiedDate(new Date());
    }
}
