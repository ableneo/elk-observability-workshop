package com.ableneo.workshops.rocket.launches.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Launch implements Serializable {
    long id;
    String name;
    String date;
    LaunchSite launchSite;
    Agency agency;
}
