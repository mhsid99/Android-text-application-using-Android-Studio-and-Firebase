package com.abc.loginapp;

public class Users
{
    public String user_name;
    public String user_age;
    public  Users()
    {

    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Users(String user_name, String user_age) {
        this.user_name = user_name;
        this.user_age = user_age;
    }

    public String getUser_age() {
        return user_age;
    }

    public void setUser_age(String user_age) {
        this.user_age = user_age;
    }
}
