package com.hotabmax.taskmanager.models;

import jakarta.persistence.*;

@Entity
@Table(name = "userroles")
public class UserRoles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;

    public UserRoles() {
    }

    public UserRoles(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return Math.toIntExact(id);
    }
}
