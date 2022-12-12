package com.wgu.scheduling.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Role {
    private Long id;
    private String roleName;
    private Timestamp createdAt;
    private String createdBy;
    private Timestamp modifiedAt;
    private String modifiedBy;
}
