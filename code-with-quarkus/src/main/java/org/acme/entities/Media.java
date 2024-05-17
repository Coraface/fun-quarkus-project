package org.acme.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class Media extends PanacheEntity {

    protected String title;
    protected String uri;
    protected String genre;

    // Constructors, getters, and setters...
}
