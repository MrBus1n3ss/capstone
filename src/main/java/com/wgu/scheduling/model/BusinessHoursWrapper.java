package com.wgu.scheduling.model;

import lombok.Data;

// Wrapper to change LocalTime to String
@Data
public class BusinessHoursWrapper {
    private Long id;

    private boolean mondayDayOff;
    private String mondayStartTime;
    private String mondayEndTime;
    private boolean tuesdayDayOff;
    private String tuesdayStartTime;
    private String tuesdayEndTime;
    private boolean wednesdayDayOff;
    private String wednesdayStartTime;
    private String wednesdayEndTime;
    private boolean thursdayDayOff;
    private String thursdayStartTime;
    private String thursdayEndTime;
    private boolean fridayDayOff;
    private String fridayStartTime;
    private String fridayEndTime;
    private boolean saturdayDayOff;
    private String saturdayStartTime;
    private String saturdayEndTime;
    private boolean sundayDayOff;
    private String sundayStartTime;
    private String sundayEndTime;
}
