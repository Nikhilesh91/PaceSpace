package com.example.nikhilesh.androidproject1;

// This is a model class used to store the information of the event

public class EventInfo {

    // properties of eventInfo class

    private String eventtitle;
    private String eventdescription;

    // getters and setters

    public String getEventtitle() {
        return eventtitle;
    }

    public void setEventtitle(String eventtitle) {
        this.eventtitle = eventtitle;
    }

    public String getEventdescription() {
        return eventdescription;
    }

    public void setEventdescription(String eventdescription) {
        this.eventdescription = eventdescription;
    }

    // default constructor

   public EventInfo(){}

   // parameterized constructor

   public EventInfo(String title, String description){
       this.eventtitle = title;
       this.eventdescription = description;
   }



}
