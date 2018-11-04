package com.multi.jpa.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * 副数据源配置
 * @author kev
 **/
@Configuration
public class SecondaryDataSourceConfiguration {

    @Bean(name = "secondaryDatasource")
    @ConfigurationProperties(prefix = "datasource.secondary")
    public DataSource dataSource(){
        return new HikariDataSource();
    }

    @Bean(name = "secondaryJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("secondaryDatasource") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}
