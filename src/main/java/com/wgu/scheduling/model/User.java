package com.wgu.scheduling.model;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalTime;

@Data
public class User {

    private Long id;
    private String userName;
    private String password;
    private String email;
    private boolean isActive;
    private int businessId;
    private int roleId;
    private boolean mondayDayOff;
    private LocalTime mondayStartTime;
    private LocalTime mondayEndTime;
    private boolean tuesdayDayOff;
    private LocalTime tuesdayStartTime;
    private LocalTime tuesdayEndTime;
    private boolean wednesdayDayOff;
    private LocalTime wednesdayStartTime;
    private LocalTime wednesdayEndTime;
    private boolean thursdayDayOff;
    private LocalTime thursdayStartTime;
    private LocalTime thursdayEndTime;
    private boolean fridayDayOff;
    private LocalTime fridayStartTime;
    private LocalTime fridayEndTime;
    private boolean saturdayDayOff;
    private LocalTime saturdayStartTime;
    private LocalTime saturdayEndTime;
    private boolean sundayDayOff;
    private LocalTime sundayStartTime;
    private LocalTime sundayEndTime;
    private Timestamp createdAt;
    private String createdBy;
    private Timestamp modifiedAt;
    private String modifiedBy;
}
