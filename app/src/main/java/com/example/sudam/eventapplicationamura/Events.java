package com.example.sudam.eventapplicationamura;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Sudam Chole on 22/02/19.
 */

@IgnoreExtraProperties
public class Events {

    public String eventName;
    public String eventAgenda;
    public String eventDate;
    public String eventTime;

    // Default constructor required for calls to
    // DataSnapshot.getValue(Events.class)
    public Events() {

    }
    public Events(String eventName, String eventAgenda,String eventDate,String eventTime) {
        this.eventName = eventName;
        this.eventAgenda = eventAgenda;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
    }
}
