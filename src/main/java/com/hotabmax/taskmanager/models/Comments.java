package com.hotabmax.taskmanager.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Schema( name="Comments", description="Model of comments which consist of data of user, comment and task id to which connect")
@Entity
@Table(name = "comments")
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String firstname;
    @Column
    private String lastname;
    @Column
    private String surname;
    @Column
    private String email;
    @Column
    private String comment;
    @Column
    private Integer taskid;

    public Comments() {
    }

    public Comments(String firstname, String lastname, String surname, String email, String comment, Integer taskid) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.surname = surname;
        this.email = email;
        this.comment = comment;
        this.taskid = taskid;
    }


    public String getEmail() {
        return email;
    }

    public String getComment() {
        return comment;
    }

    public Integer getTaskid() {
        return taskid;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setTaskid(Integer taskid) {
        this.taskid = taskid;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getSurname() {
        return surname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getId() {
        return Math.toIntExact(id);
    }
}
