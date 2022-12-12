package com.wgu.scheduling.repository;

import com.wgu.scheduling.model.*;
import com.wgu.scheduling.repository.mapper.RoleRowMapper;
import com.wgu.scheduling.repository.mapper.UserRowMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public User getById(long id) {
        String sql = "Select * from users where user_id=?";

        return jdbcTemplate.queryForObject(
                sql, new UserRowMapper(), id);
    }

    public User findByUserName(String username) {
        String sql = "Select * from users where user_name=?";
        return jdbcTemplate.queryForObject(
                sql, new UserRowMapper(), username);
    }

    public Role getRoleById(long id) {
        String sql = "Select * from roles where role_id=?";
        return jdbcTemplate.queryForObject(
                sql, new RoleRowMapper(), id);
    }

    public List<Role> getRoles() {
        String sql = "Select * from roles";
        return jdbcTemplate.query(sql, new RoleRowMapper());
    }

    public List<User> findAll() {
        String sql = "Select * from users";
        return jdbcTemplate.query(
                sql, new UserRowMapper());
    }

    public void save(User user) {

        String sql = "insert into users (" +
                "user_name, " +
                "user_password, " +
                "user_email, " +
                "is_active, " +
                "user_business_hours_id, " +
                "role_id, " +
                "monday_day_off, " +
                "monday_start_time, " +
                "monday_end_time, " +
                "tuesday_day_off, " +
                "tuesday_start_time, " +
                "tuesday_end_time, " +
                "wednesday_day_off, " +
                "wednesday_start_time, " +
                "wednesday_end_time, " +
                "thursday_day_off, " +
                "thursday_start_time, " +
                "thursday_end_time, " +
                "friday_day_off, " +
                "friday_start_time, " +
                "friday_end_time, " +
                "saturday_day_off, " +
                "saturday_start_time, " +
                "saturday_end_time, " +
                "sunday_day_off, " +
                "sunday_start_time, " +
                "sunday_end_time, " +
                "created_at, " +
                "created_by, " +
                "modified_at, " +
                "modified_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, NOW(), ?)";

        if(user.getPassword() == null || user.getPassword().isEmpty()) {
            user.setPassword(DigestUtils.sha256Hex("12Three45"));
        }


        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setBoolean(4, user.isActive());
            ps.setInt(5, user.getBusinessId());
            ps.setInt(6, user.getRoleId());
            ps.setBoolean(7, user.isMondayDayOff());
            ps.setTime(8, Time.valueOf(user.getMondayStartTime()));
            ps.setTime(9, Time.valueOf(user.getMondayEndTime()));
            ps.setBoolean(10, user.isTuesdayDayOff());
            ps.setTime(11, Time.valueOf(user.getTuesdayStartTime()));
            ps.setTime(12, Time.valueOf(user.getTuesdayEndTime()));
            ps.setBoolean(13, user.isWednesdayDayOff());
            ps.setTime(14, Time.valueOf(user.getWednesdayStartTime()));
            ps.setTime(15, Time.valueOf(user.getWednesdayEndTime()));
            ps.setBoolean(16, user.isThursdayDayOff());
            ps.setTime(17, Time.valueOf(user.getThursdayStartTime()));
            ps.setTime(18, Time.valueOf(user.getThursdayEndTime()));
            ps.setBoolean(19, user.isFridayDayOff());
            ps.setTime(20, Time.valueOf(user.getFridayStartTime()));
            ps.setTime(21, Time.valueOf(user.getFridayEndTime()));
            ps.setBoolean(22, user.isSaturdayDayOff());
            ps.setTime(23, Time.valueOf(user.getSaturdayStartTime()));
            ps.setTime(24, Time.valueOf(user.getSaturdayEndTime()));
            ps.setBoolean(25, user.isSundayDayOff());
            ps.setTime(26, Time.valueOf(user.getSundayStartTime()));
            ps.setTime(27, Time.valueOf(user.getSundayEndTime()));
            if(CurrentUser.userName == null || CurrentUser.userName.isEmpty()){
                ps.setString(28, user.getCreatedBy());
                ps.setString(29, user.getModifiedBy());
            } else {
                ps.setString(28, CurrentUser.userName);
                ps.setString(29, CurrentUser.userName);
            }


            return ps;
        });

    }

    public void updatePassword(User user) {
        String sql = "update users set " +
                "user_password = ? " +
                "where user_id = ?";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getPassword());
            ps.setLong(2, user.getId());
            return ps;
        });
    }

    public void update(User user) {
        String sql = "update users set " +
                "user_name = ?," +
                "user_email = ?, " +
                "is_active = ?, " +
                "user_business_hours_id = ?, " +
                "role_id = ?, " +
                "monday_day_off = ?, " +
                "monday_start_time = ?, " +
                "monday_end_time = ?, " +
                "tuesday_day_off = ?, " +
                "tuesday_start_time = ?, " +
                "tuesday_end_time = ?, " +
                "wednesday_day_off = ?, " +
                "wednesday_start_time = ?, " +
                "wednesday_end_time = ?, " +
                "thursday_day_off = ?, " +
                "thursday_start_time = ?, " +
                "thursday_end_time = ?, " +
                "friday_day_off = ?, " +
                "friday_start_time = ?, " +
                "friday_end_time = ?, " +
                "saturday_day_off = ?, " +
                "saturday_start_time = ?, " +
                "saturday_end_time = ?, " +
                "sunday_day_off = ?, " +
                "sunday_start_time = ?, " +
                "sunday_end_time = ?, " +
                "modified_at = NOW(), " +
                "modified_by = ? " +
                "where user_id = ?";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getEmail());
            ps.setBoolean(3, user.isActive());
            ps.setInt(4, user.getBusinessId());
            ps.setInt(5, user.getRoleId());
            ps.setBoolean(6, user.isMondayDayOff());
            ps.setTime(7, Time.valueOf(user.getMondayStartTime()));
            ps.setTime(8, Time.valueOf(user.getMondayEndTime()));
            ps.setBoolean(9, user.isTuesdayDayOff());
            ps.setTime(10, Time.valueOf(user.getTuesdayStartTime()));
            ps.setTime(11, Time.valueOf(user.getTuesdayEndTime()));
            ps.setBoolean(12, user.isWednesdayDayOff());
            ps.setTime(13, Time.valueOf(user.getWednesdayStartTime()));
            ps.setTime(14, Time.valueOf(user.getWednesdayEndTime()));
            ps.setBoolean(15, user.isThursdayDayOff());
            ps.setTime(16, Time.valueOf(user.getThursdayStartTime()));
            ps.setTime(17, Time.valueOf(user.getThursdayEndTime()));
            ps.setBoolean(18, user.isFridayDayOff());
            ps.setTime(19, Time.valueOf(user.getFridayStartTime()));
            ps.setTime(20, Time.valueOf(user.getFridayEndTime()));
            ps.setBoolean(21, user.isSaturdayDayOff());
            ps.setTime(22, Time.valueOf(user.getSaturdayStartTime()));
            ps.setTime(23, Time.valueOf(user.getSaturdayEndTime()));
            ps.setBoolean(24, user.isSundayDayOff());
            ps.setTime(25, Time.valueOf(user.getSundayStartTime()));
            ps.setTime(26, Time.valueOf(user.getSundayEndTime()));
            ps.setString(27, CurrentUser.userName);
            ps.setLong(28, user.getId());

            return ps;
        });
    }

    public void deleteById(long id) {
        String sql = "DELETE FROM users WHERE user_id=?";
        jdbcTemplate.update(sql, id);
    }
}
