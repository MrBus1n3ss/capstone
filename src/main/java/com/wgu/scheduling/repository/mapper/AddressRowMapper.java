package com.wgu.scheduling.repository.mapper;

import com.wgu.scheduling.model.Address;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressRowMapper implements RowMapper<Address> {
    @Override
    public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
        Address address = new Address();

        address.setId(rs.getLong("address_id"));
        address.setAddress1(rs.getString("address_1"));
        address.setAddress2(rs.getString("address_2"));
        address.setCity(rs.getString("city"));
        address.setPostalCode(rs.getString("postal_code"));
        address.setStateId(rs.getLong("state_id"));
        address.setCustomerId(rs.getLong("customer_id"));

        return address;

    }
}
