package com.example.model;

import java.io.Serializable;

public class Contact implements Serializable {


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    private String name;
    private String phoneNumber;
    private String group;
    private boolean like;

    public Contact(String name, String phoneNumber, String group, boolean like){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.group = group;
        this.like = like;
    }

    public Contact(String name, String phoneNumber){
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Contact(){
        this.like =false;
    }
}
