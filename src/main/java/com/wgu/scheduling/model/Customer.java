package com.wgu.scheduling.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Customer {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Address address;
    private Phone mobilePhone;
    private Phone workPhone;
    private Phone homePhone;
    private Timestamp createdAt;
    private String createdBy;
    private Timestamp modifiedAt;
    private String modifiedBy;

}
