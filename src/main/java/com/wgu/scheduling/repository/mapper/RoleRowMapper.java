package com.wgu.scheduling.repository.mapper;

import com.wgu.scheduling.model.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRowMapper implements RowMapper<Role> {
    @Override
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        Role role = new Role();

        role.setId(rs.getLong("role_id"));
        role.setRoleName(rs.getString("role_name"));
        role.setCreatedAt(rs.getTimestamp("created_at"));
        role.setCreatedBy(rs.getString("created_by"));
        role.setModifiedAt(rs.getTimestamp("modified_at"));
        role.setModifiedBy(rs.getString("modified_by"));

        return role;
    }
}
