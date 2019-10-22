package com.example.jdbctemplate.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * DataSourceOneConfig
 *
 * @author star
 */
@Configuration
public class DataSourceOneConfig {

    @Bean(name = "DataSourceOne")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.druid.one")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "JdbcTemplateOne")
    @Primary
    public JdbcTemplate jdbcTemplate(@Qualifier("DataSourceOne") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


}
