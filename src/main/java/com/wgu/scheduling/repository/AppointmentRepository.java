package com.wgu.scheduling.repository;

import com.wgu.scheduling.model.Appointment;
import com.wgu.scheduling.model.CurrentUser;
import com.wgu.scheduling.repository.mapper.AppointmentRowMapper;
import com.wgu.scheduling.util.Tools;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

@Log4j2
@Repository
public class AppointmentRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Appointment getById(long id) {
        String sql = "Select * from appointments where appointment_id=?";
        return jdbcTemplate.queryForObject(
                sql, new AppointmentRowMapper(), id);
    }

    public List<Appointment> findAllByUserBetweenStartAndEndTime(long id, Timestamp start, Timestamp end) {
        String sql = "Select * from appointments " +
                "where user_id=? " +
                "and start_timestamp between ? and ? " +
                "or end_timestamp between ? and ?";
        return jdbcTemplate.query(sql, new AppointmentRowMapper(), id, start, end, start, end);
    }

    public List<Appointment> findAll(long user_id) {
        String sql = "Select * from appointments where user_id=?";
        return jdbcTemplate.query(
                sql, new AppointmentRowMapper(), user_id);
    }

    public List<Appointment> all() {
        String sql = "Select * from appointments";
        return jdbcTemplate.query(
                sql, new AppointmentRowMapper());
    }

    public void save(Appointment appointment) {
        String appointmentSql = "insert into appointments ( " +
                "appointment_title, " +
                "appointment_description, " +
                "appointment_location, " +
                "preferred_contact, " +
                "appointment_type, " +
                "appointment_url, " +
                "start_timestamp, " +
                "end_timestamp, " +
                "user_id, " +
                "customer_id, " +
                "created_at, " +
                "created_by, " +
                "modified_at, " +
                "modified_by) " +
                "values(?, ?, ?, ?, ?, ?, ?, ? ,?, ?, now(), ?, now(), ?" +
                ")";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(appointmentSql, new String[]{"appointment_id"});
            ps.setString(1, appointment.getTitle());
            ps.setString(2, appointment.getDescription());
            ps.setString(3, appointment.getLocation());
            ps.setString(4, appointment.getContact());
            ps.setString(5, appointment.getType());
            ps.setString(6, appointment.getUrl());
            ps.setTimestamp(7, Tools.convertStringToTimestamp(appointment.getStart()));
            ps.setTimestamp(8, Tools.convertStringToTimestamp(appointment.getEnd()));
            ps.setInt(9, CurrentUser.id.intValue());
            ps.setInt(10, (int)appointment.getCustomerId());
            ps.setString(11, CurrentUser.userName);
            ps.setString(12, CurrentUser.userName);

            return ps;
        });

        log.info("Saved Appointment");
    }

    public void update(Appointment appointment) {
        String sql = "update appointments set " +
                "appointment_title = ?," +
                "appointment_description = ?, " +
                "appointment_location = ?, " +
                "preferred_contact = ?, " +
                "appointment_type = ?, " +
                "appointment_url = ?, " +
                "start_timestamp = ?, " +
                "end_timestamp = ?, " +
                "customer_id = ?, " +
                "modified_at = NOW(), " +
                "modified_by = ?" +
                "where appointment_id = ?";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, appointment.getTitle());
            ps.setString(2, appointment.getDescription());
            ps.setString(3, appointment.getLocation());
            ps.setString(4, appointment.getContact());
            ps.setString(5, appointment.getType());
            ps.setString(6, appointment.getUrl());
            ps.setTimestamp(7, Tools.convertStringToTimestamp(appointment.getStart()));
            ps.setTimestamp(8, Tools.convertStringToTimestamp(appointment.getEnd()));
            ps.setLong(9, appointment.getCustomerId());
            ps.setString(10, CurrentUser.userName);
            ps.setLong(11, appointment.getAppointmentId());

            return ps;
        });
    }

    public void deleteById(long id) {
        String sql = "DELETE FROM appointments WHERE appointment_id=?";
        jdbcTemplate.update(sql, id);
    }
}
