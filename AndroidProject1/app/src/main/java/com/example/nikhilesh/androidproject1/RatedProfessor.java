package com.example.nikhilesh.androidproject1;

import java.util.List;

public class RatedProfessor {
//this class is for the rated values of professor
    String ratedDescription;
    List<String> ratings;

    public List<String> getRatings() {
        return ratings;
    }

    public void setRatings(List<String> ratings) {
        this.ratings = ratings;
    }

    RatedProfessor(String ratedDescription){
        this.ratedDescription =ratedDescription;
    }

}
