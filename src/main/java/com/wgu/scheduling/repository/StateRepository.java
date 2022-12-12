package com.wgu.scheduling.repository;

import com.wgu.scheduling.model.UsState;
import com.wgu.scheduling.repository.mapper.StateRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StateRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<UsState> findAll() {
        return jdbcTemplate.query(
                "select * from states", new StateRowMapper()
        );
    }
}
