package com.wgu.scheduling.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class UsState implements Comparable<UsState> {
    private Long stateId;
    private String stateName;
    private String stateAbbreviation;

    @Override
    public int compareTo(@NonNull UsState state) {
        if(getStateName() == null || state.getStateName() == null) {
            return 0;
        }
        return getStateName().compareTo(state.getStateName());
    }
}
