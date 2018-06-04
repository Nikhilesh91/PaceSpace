package com.example.nikhilesh.androidproject1;

public class UserInfo {

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGraduationyear() {
        return graduationyear;
    }

    public void setGraduationyear(String graduationyear) {
        this.graduationyear = graduationyear;
    }

    private String firstname;
    private String lastname;
    private String email;
    private String graduationyear;
    private String password;
    private String url;

    public String geturl() {
        return url;
    }

    public void seturl(String url) {
        this.url = url;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }




    // declare properties for User class





    // generate getters and setters for User class





    // default contruxctor
    public UserInfo(){ }


    // parametrized contructor
    public UserInfo(String firstname, String lastname, String email, String graduationyear , String password, String url){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.graduationyear = graduationyear;
        this.password = password;
        this.url = url;
    }


}
