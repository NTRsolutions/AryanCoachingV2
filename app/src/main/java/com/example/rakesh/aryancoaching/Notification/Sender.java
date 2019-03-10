package com.example.rakesh.aryancoaching.Notification;

import android.app.Notification;

public class Sender {
    public com.example.rakesh.aryancoaching.Notification.Notification notification;
    public  String to;
    public Sender(){

    }
    public Sender(String to,com.example.rakesh.aryancoaching.Notification.Notification notification){
        this.notification = notification;
        this.to = to;

    }

    public com.example.rakesh.aryancoaching.Notification.Notification getNotification() {
        return notification;
    }

    public String getTo() {
        return to;
    }
}
