package com.example.nikhilesh.androidproject1;

import java.util.ArrayList;
import java.util.List;


public class Professor {

    String profName;
    String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    String ratings;
    String subject;


    public String getProfListUserId() {
        return profListUserId;
    }

    public void setProfListUserId(String profListUserId) {
        this.profListUserId = profListUserId;
    }

    String profListUserId;

    public String getProfName() {
        return profName;
    }

    public void setProfName(String profName) {
        this.profName = profName;
    }

    Professor(String name){
        profName = name;
    }
    Professor(String name,String profListUserId){
        this.profListUserId=profListUserId;
        profName=name;

    }
    Professor(String description,String ratings,String subject){
        this.description=description;
        this.ratings =ratings;
        this.subject=subject;
    }

    Professor(){
    }

}
