package org.example;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class testEntity {
    @Id
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
