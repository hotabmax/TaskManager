package com.hotabmax.taskmanager.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private Integer statusid;
    @Column
    private Integer priorityid;
    @Column
    private Integer customerid;
    @Column
    private Integer executorid;

    public Tasks() {
    }

    public Tasks(String name, String description, Integer statusid, Integer priorityid, Integer customerid, Integer executorid) {
        this.name = name;
        this.description = description;
        this.statusid = statusid;
        this.priorityid = priorityid;
        this.customerid = customerid;
        this.executorid = executorid;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getStatusid() {
        return statusid;
    }

    public Integer getPriorityid() {
        return priorityid;
    }

    public Integer getCustomerid() {
        return customerid;
    }

    public Integer getExecutorid() {
        return executorid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatusid(Integer statusid) {
        this.statusid = statusid;
    }

    public void setPriorityid(Integer priorityid) {
        this.priorityid = priorityid;
    }

    public void setCustomerid(Integer customerid) {
        this.customerid = customerid;
    }

    public void setExecutorid(Integer executorid) {
        this.executorid = executorid;
    }

    public Integer getId() {
        return Math.toIntExact(id);
    }
}
