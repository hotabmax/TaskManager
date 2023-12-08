package com.hotabmax.taskmanager.models;


import jakarta.persistence.*;

@Entity
@Table(name = "userdata")
public class User{

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
    private String password;
    @Column
    private Integer userroleid;

    public User(String firstname,
                    String lastname,
                    String surname,
                    String email,
                    String password,
                    int userroleid) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.userroleid = userroleid;
    }

    public User() {
    }



    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Integer getUserroleid() {
        return userroleid;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserroleid(Integer userroleid) {
        this.userroleid = userroleid;
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
