package com.wgu.scheduling.repository.mapper;

import com.wgu.scheduling.model.Appointment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentRowMapper  implements RowMapper<Appointment> {
    @Override
    public Appointment mapRow(ResultSet rs, int rowNum) throws SQLException {
        Appointment appointment = new Appointment();

        appointment.setAppointmentId(rs.getLong("appointment_id"));
        appointment.setTitle(rs.getString("appointment_title"));
        appointment.setDescription(rs.getString("appointment_description"));
        appointment.setLocation(rs.getString("appointment_location"));
        appointment.setContact(rs.getString("preferred_contact"));
        appointment.setType(rs.getString("appointment_type"));
        appointment.setUrl(rs.getString("appointment_url"));
        appointment.setStart(rs.getTimestamp("start_timestamp").toString());
        appointment.setEnd(rs.getTimestamp("end_timestamp").toString());
        appointment.setUserId(rs.getLong("user_id"));
        appointment.setCustomerId(rs.getLong("customer_id"));
        appointment.setCreatedAt(rs.getTimestamp("created_at"));
        appointment.setCreatedBy(rs.getString("created_by"));
        appointment.setModifiedAt(rs.getTimestamp("modified_at"));
        appointment.setModifiedBy(rs.getString("modified_by"));

        return appointment;
    }
}
