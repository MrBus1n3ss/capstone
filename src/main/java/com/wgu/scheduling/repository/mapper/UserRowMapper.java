package com.wgu.scheduling.repository.mapper;

import com.wgu.scheduling.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        User user = new User();
        user.setId(rs.getLong("user_id"));
        user.setUserName(rs.getString("user_name"));
        user.setPassword(rs.getString("user_password"));
        user.setEmail(rs.getString("user_email"));
        user.setActive(rs.getBoolean("is_active"));
        user.setBusinessId(rs.getInt("user_business_hours_id"));
        user.setRoleId(rs.getInt("role_id"));
        user.setMondayStartTime(LocalTime.parse(rs.getString("monday_start_time")));
        user.setMondayEndTime(LocalTime.parse(rs.getString("monday_end_time")));
        user.setMondayDayOff(rs.getBoolean("monday_day_off"));
        user.setTuesdayStartTime(LocalTime.parse(rs.getString("tuesday_start_time")));
        user.setTuesdayEndTime(LocalTime.parse(rs.getString("tuesday_end_time")));
        user.setTuesdayDayOff(rs.getBoolean("tuesday_day_off"));
        user.setWednesdayStartTime(LocalTime.parse(rs.getString("wednesday_start_time")));
        user.setWednesdayEndTime(LocalTime.parse(rs.getString("wednesday_end_time")));
        user.setWednesdayDayOff(rs.getBoolean("wednesday_day_off"));
        user.setThursdayStartTime(LocalTime.parse(rs.getString("thursday_start_time")));
        user.setThursdayEndTime(LocalTime.parse(rs.getString("thursday_end_time")));
        user.setThursdayDayOff(rs.getBoolean("thursday_day_off"));
        user.setFridayStartTime(LocalTime.parse(rs.getString("friday_start_time")));
        user.setFridayEndTime(LocalTime.parse(rs.getString("friday_end_time")));
        user.setFridayDayOff(rs.getBoolean("friday_day_off"));
        user.setSaturdayStartTime(LocalTime.parse(rs.getString("saturday_start_time")));
        user.setSaturdayEndTime(LocalTime.parse(rs.getString("saturday_end_time")));
        user.setSaturdayDayOff(rs.getBoolean("saturday_day_off"));
        user.setSundayStartTime(LocalTime.parse(rs.getString("sunday_start_time")));
        user.setSundayEndTime(LocalTime.parse(rs.getString("sunday_end_time")));
        user.setSundayDayOff(rs.getBoolean("sunday_day_off"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        user.setCreatedBy(rs.getString("created_by"));
        user.setModifiedAt(rs.getTimestamp("modified_at"));
        user.setModifiedBy(rs.getString("modified_by"));

        return user;
    }
}
