package be.beme.schn.persistence.daoimpl.mapper;

import be.beme.schn.narrative.component.Diagram;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Dotista on 19-08-16.
 */
@Component //singleton scope by default
public  final class DiagramMapper implements RowMapper<Diagram> {

    public Diagram mapRow(ResultSet rs, int rowNum) throws SQLException {
        Diagram diagram = new Diagram();
        diagram.setId(rs.getInt("id"));
        diagram.setUser_id(rs.getInt("user_id"));
        diagram.setTitle(rs.getString("title"));
        return diagram;
    }
}