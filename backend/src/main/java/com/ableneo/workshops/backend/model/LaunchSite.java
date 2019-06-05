package com.ableneo.workshops.backend.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class LaunchSite implements Serializable {
    String name;
    String country;
}
