package com.wgu.scheduling.model;

import lombok.Data;

import java.time.LocalTime;

@Data
public class BusinessHoursSegment {
    private boolean isDayOff;
    private LocalTime startTime;
    private LocalTime endTime;

}
