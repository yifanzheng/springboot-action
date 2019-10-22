package com.example.jdbctemplate.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * DataSourceTwoConfig
 *
 * @author star
 */
@Configuration
public class DataSourceTwoConfig {

    @Bean(name = "DataSourceTwo")
    @ConfigurationProperties(prefix = "spring.datasource.druid.two")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "JdbcTemplateTwo")
    public JdbcTemplate jdbcTemplate(@Qualifier("DataSourceTwo") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
