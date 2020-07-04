package com.abc.loginapp;

public class UserProfile
{
    public String user_age;
    public String user_email;
    public String user_name;
    public  UserProfile()
    {
    }

    public UserProfile(String u_age, String u_email, String u_name)
    {
        this.user_age = u_age;
        this.user_email = u_email;
        this.user_name = u_name;

    }


    public String getUser_age() {
        return user_age;
    }

    public void setUser_age(String u_age) {
        this.user_age = u_age;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String u_email) {
        this.user_email = u_email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String u_name) {
        this.user_name = u_name;
    }
}
