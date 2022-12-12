package com.wgu.scheduling.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Phone {
    private Long id;
    private String phoneName;
    private String phoneNumber;
    private Long customerId;
    private Timestamp createdAt;
    private String createdBy;
    private Timestamp modifiedAt;
    private String modifiedBy;

}
