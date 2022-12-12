package com.wgu.scheduling.repository.mapper;

import com.wgu.scheduling.model.Phone;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class PhoneRowMapper implements RowMapper<Phone> {
    @Override
    public Phone mapRow(ResultSet rs, int rowNum) throws SQLException {
        Phone phone = new Phone();

        phone.setId(rs.getLong("phone_id"));
        phone.setPhoneName(rs.getString("phone_name"));
        phone.setPhoneNumber(rs.getString("phone_number"));
        phone.setCustomerId(rs.getLong("customer_id"));

        return phone;
    }
}
