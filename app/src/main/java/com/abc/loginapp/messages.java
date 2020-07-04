package com.abc.loginapp;
public class messages
{
    private String message;
        private boolean seen;
        private long time;
                private String type;
                private String from;
    public messages(String from)
    {
        this.from=from;
    }
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public messages(String message, Boolean seen, long time, String type)
    {
        this.message=message;
        this.seen=seen;
        this.time=time;
        this.type=type;


    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public messages()//*
    {

    }


}
