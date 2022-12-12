package com.wgu.scheduling.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Address {
    private Long id;
    private String address1;
    private String address2;
    private String city;
    private String postalCode;
    private Long stateId;
    private Long customerId;
    private Timestamp createdAt;
    private String createdBy;
    private Timestamp modifiedAt;
    private String modifiedBy;
}
