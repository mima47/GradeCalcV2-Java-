package com.marapps.models;

import com.marapps.api.Institute;

import java.io.Serializable;

public class User implements Serializable {
    String username;
    String password;
    Institute institute;
    Boolean StayLoggedIn = false;

    public void print(){
        System.out.println("------------------------");
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("Institute: " + institute);
        System.out.println("StayLoggedIn: " + StayLoggedIn);
        System.out.println("------------------------");
        System.out.println();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public Boolean getStayLoggedIn() {
        return StayLoggedIn;
    }

    public void setStayLoggedIn(Boolean stayLoggedIn) {
        StayLoggedIn = stayLoggedIn;
    }
}
