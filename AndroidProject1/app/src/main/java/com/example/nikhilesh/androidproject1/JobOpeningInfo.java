package com.example.nikhilesh.androidproject1;

// model class for job opoenings

public class JobOpeningInfo {

    // properties of the JobOpeninginfo class

    private String contact;
    private String qualification;
    private String title;

    // generate the getters and setters for hte properties

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // default constructor

    public JobOpeningInfo(){}

    // parameterized constructor

    public JobOpeningInfo(String contact,String qualification,String title){
        this.contact = contact;
        this.qualification = qualification;
        this.title = title;
    }

}
