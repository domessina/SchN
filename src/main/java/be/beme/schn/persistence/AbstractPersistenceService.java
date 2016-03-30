package be.beme.schn.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by Dorito on 26-03-16.
 */
public abstract class AbstractPersistenceService  {

    @Autowired
    protected JdbcTemplate jdbcTemplate;




}
