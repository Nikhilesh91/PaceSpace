package com.example.nikhilesh.androidproject1;

public class ViewCourse {
    String courseName;
    String description;
    String rating;

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    String userId;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    ViewCourse(){

    }
    ViewCourse(String courseName,String userId){
        this.courseName=courseName;
        this.userId=userId;
    }

}