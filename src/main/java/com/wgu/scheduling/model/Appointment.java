package com.wgu.scheduling.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Appointment {
    private Long appointmentId;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private String url;
    private String start;
    private String end;
    private long userId;
    private long customerId;
    private Timestamp createdAt;
    private String createdBy;
    private Timestamp modifiedAt;
    private String modifiedBy;
}
