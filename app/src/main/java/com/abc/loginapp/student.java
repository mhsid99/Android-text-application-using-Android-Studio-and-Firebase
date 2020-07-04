package com.abc.loginapp;

public class student
{
    private String user_name;
    private String user_age;
    private  String user_email;

    public student()
    {
    }
    public student(String user_name,String user_age,String user_email){

        this.user_name = user_name;
        this.user_age= user_age;
        this.user_email=user_email;

    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_age() {
        return user_age;
    }

    public void setUser_age(String user_age) {
        this.user_age = user_age;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
}
