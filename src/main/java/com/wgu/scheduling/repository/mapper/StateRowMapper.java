package com.wgu.scheduling.repository.mapper;

import com.wgu.scheduling.model.UsState;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StateRowMapper implements RowMapper<UsState> {

    @Override
    public UsState mapRow(ResultSet rs, int rowNum) throws SQLException {
        UsState usState = new UsState();
        usState.setStateId(rs.getLong("state_id"));
        usState.setStateName(rs.getString("state_name"));
        usState.setStateAbbreviation(rs.getString("state_abbreviation"));

        return usState;
    }
}
