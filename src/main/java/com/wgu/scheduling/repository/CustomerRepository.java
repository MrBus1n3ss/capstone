package com.wgu.scheduling.repository;

import com.wgu.scheduling.model.*;
import com.wgu.scheduling.repository.mapper.AddressRowMapper;
import com.wgu.scheduling.repository.mapper.CustomerRowMapper;
import com.wgu.scheduling.repository.mapper.PhoneRowMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Log4j2
@Repository
public class CustomerRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Customer getById(long id) {

        String sql = "select * from customers where customer_id=?";
        return jdbcTemplate.queryForObject(
                sql, new CustomerRowMapper(), id);
    }

    public List<Customer> findAll() {
        String sql = "Select * from customers";
        return jdbcTemplate.query(
                sql, new CustomerRowMapper());
    }

    public void save(Customer customer) {
        String customerSql = "insert into customers (" +
                "first_name, " +
                "last_name, " +
                "email, " +
                "created_at, " +
                "created_by, " +
                "modified_at, " +
                "modified_by) " +
                "VALUES (?, ?, ?, NOW(), ?, NOW(), ?)";

        String addressSql = "insert into addresses (" +
                "address_1, " +
                "address_2, " +
                "city, " +
                "postal_code, " +
                "state_id, " +
                "customer_id, " +
                "created_at, " +
                "created_by, " +
                "modified_at, " +
                "modified_by) " +
                "values(?, ?, ?, ?, ?, ?, now(), ?, now(), ?" +
                ")";

        String phoneSql = "insert into phones (" +
                "phone_name, " +
                "phone_number, " +
                "customer_id, " +
                "created_at, " +
                "created_by, " +
                "modified_at, " +
                "modified_by) " +
                "values(?, ?, ?, now(), ?, now(), ?" +
                ")";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(customerSql, new String[]{"customer_id"});
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getEmail());
            ps.setString(4, CurrentUser.userName);
            ps.setString(5, CurrentUser.userName);

            return ps;
        }, keyHolder);

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(addressSql, new String[]{"address_id"});
            Address address = customer.getAddress();
            ps.setString(1, address.getAddress1());
            ps.setString(2, address.getAddress2());
            ps.setString(3, address.getCity());
            ps.setString(4, address.getPostalCode());
            ps.setInt(5, address.getStateId().intValue());
            ps.setInt(6, (int) Objects.requireNonNull(keyHolder.getKey()).longValue());
            ps.setString(7, CurrentUser.userName);
            ps.setString(8, CurrentUser.userName);

            return ps;
        });


        List<Phone> phones = new ArrayList<>();
        if (!customer.getMobilePhone().getPhoneNumber().trim().isEmpty()) {
            phones.add(customer.getMobilePhone());
        }
        if (!customer.getHomePhone().getPhoneNumber().trim().isEmpty()) {
            phones.add(customer.getHomePhone());
        }
        if (!customer.getWorkPhone().getPhoneNumber().trim().isEmpty()) {
            phones.add(customer.getWorkPhone());
        }


        for (Phone phone : phones) {
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(phoneSql, new String[]{"phone_id"});
                ps.setString(1, phone.getPhoneName());
                ps.setString(2, phone.getPhoneNumber());
                ps.setInt(3, (int) Objects.requireNonNull(keyHolder.getKey()).longValue());
                ps.setString(4, CurrentUser.userName);
                ps.setString(5, CurrentUser.userName);

                return ps;
            });
        }

        log.info("Saved Customer");
    }

    public void update(Customer customer) {
        String customerUpdateSql = "update customers set " +
                "first_name = ?, " +
                "last_name = ?, " +
                "email = ?, " +
                "modified_at = NOW(), " +
                "modified_by = ? " +
                "where customer_id = ?";

        String addressUpdateSql = "update addresses set " +
                "address_1 = ?, " +
                "address_2 = ?, " +
                "city = ?," +
                "postal_code = ?, " +
                "state_id = ?, " +
                "modified_at = NOW(), " +
                "modified_by = ? " +
                "where address_id = ?";

        String phoneUpdateSql = "update phones set " +
                "phone_name = ?, " +
                "phone_number = ?, " +
                "modified_at = NOW(), " +
                "modified_by = ? " +
                "where phone_id = ?";

        String phoneCreateSql = "insert into phones (" +
                "phone_name, " +
                "phone_number, " +
                "customer_id, " +
                "created_at, " +
                "created_by, " +
                "modified_at, " +
                "modified_by) " +
                "values(?, ?, ?, now(), ?, now(), ?" +
                ")";

        // Update Customer
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(customerUpdateSql);
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getEmail());
            ps.setString(4, CurrentUser.userName);
            ps.setInt(5, customer.getId().intValue());

            return ps;
        });

        // Update Address
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(addressUpdateSql);
            ps.setString(1, customer.getAddress().getAddress1());
            ps.setString(2, customer.getAddress().getAddress2());
            ps.setString(3, customer.getAddress().getCity());
            ps.setString(4, customer.getAddress().getPostalCode());
            ps.setInt(5, customer.getAddress().getStateId().intValue());
            ps.setString(6, CurrentUser.userName);
            ps.setInt(7, customer.getAddress().getId().intValue());

            return ps;
        });

        // Update or Create Phone
        List<Phone> phones = new ArrayList<>();
        phones.add(customer.getWorkPhone());
        phones.add(customer.getHomePhone());
        phones.add(customer.getMobilePhone());

        for(Phone phone: phones) {
            if(phone.getId() == null) {
                // Create phone if it doesn't exist
                jdbcTemplate.update(con -> {
                    PreparedStatement ps = con.prepareStatement(phoneCreateSql, new String[]{"phone_id"});
                    ps.setString(1, phone.getPhoneName());
                    ps.setString(2, phone.getPhoneNumber());
                    ps.setInt(3, customer.getId().intValue());
                    ps.setString(4, CurrentUser.userName);
                    ps.setString(5, CurrentUser.userName);

                    return ps;
                });
            } else {
                jdbcTemplate.update(con -> {
                    PreparedStatement ps = con.prepareStatement(phoneUpdateSql);
                    ps.setString(1, phone.getPhoneName());
                    ps.setString(2, phone.getPhoneNumber());
                    ps.setString(3, CurrentUser.userName);
                    ps.setInt(4, phone.getId().intValue());

                    return ps;
                });
            }
        }
    }

    public void deleteById(long id) {
        String sql = "DELETE FROM customers WHERE customer_id=?";
        jdbcTemplate.update(sql, id);
    }

    public Address getAddress(long customerId) {
        String sql = "select * from addresses where customer_id=?";
        return jdbcTemplate.queryForObject(
                sql, new AddressRowMapper(), customerId);
    }

    public List<Phone> getPhone(long customerId) {
        String sql = "select * from phones where customer_id=?";
        return jdbcTemplate.query(sql, new PhoneRowMapper(), customerId);
    }
}
