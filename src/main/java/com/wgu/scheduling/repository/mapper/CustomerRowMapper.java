package com.wgu.scheduling.repository.mapper;

import com.wgu.scheduling.model.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRowMapper implements RowMapper<Customer> {

    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();

        customer.setId(rs.getLong("customer_id"));
        customer.setFirstName(rs.getString("first_name"));
        customer.setLastName(rs.getString("last_name"));
        customer.setEmail(rs.getString("email"));
        customer.setCreatedAt(rs.getTimestamp("created_at"));
        customer.setCreatedBy(rs.getString("created_by"));
        customer.setModifiedAt(rs.getTimestamp("modified_at"));
        customer.setModifiedBy(rs.getString("modified_by"));

        return customer;
    }
}
