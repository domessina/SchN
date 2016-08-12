package be.beme.schn.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by Dorito on 26-03-16.
 *
 * For @Transactional see: 10.5.3 Rolling back a declarative transaction Spring doc
 */
public abstract class AbstractPersistenceService  {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

//TODO remarque que durant lancer le rpogramme s'affiche à al fin SQLErrorCodes loaded: [DB2, Derby, H2, HSQL, Informix, MS-SQL, MySQL, Oracle, PostgreSQL, Sybase, Hana... utiliser SQLErrorCodes pour dire de charger que Postgres

}
