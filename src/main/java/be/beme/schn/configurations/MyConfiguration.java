package be.beme.schn.configurations;

import be.beme.schn.dao.DiagramDao;
import be.beme.schn.daoimpl.DiagramDaoImpl;
import com.vaadin.spring.annotation.EnableVaadin;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by Dorito on 17-03-16.
 */
@org.springframework.context.annotation.Configuration
@EnableVaadin                      //importe VaadinConfiguration
public  class MyConfiguration {


    @Autowired
    private PGSimpleDataSource dataSource;

    @Bean
    public DiagramDao diagramServiceImpl()
    {
        return new DiagramDaoImpl();
    }


    @Bean
    public JdbcTemplate jdbcTemplate()
    {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public PGSimpleDataSource getDataSource(){
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/tfe");
        dataSource.setUser("dorito");
        dataSource.setPassword("barbe");
        return dataSource;
    }
}
