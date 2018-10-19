package com.example.c3p0.config;

import ch.qos.logback.classic.turbo.TurboFilter;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * QueryRunner配置类
 *
 * @author kevin
 * @date 2018-10-19 21:42
 **/
@Configuration
public class QueryRunnerConfiguration {

    @Autowired
    private DataSource dataSource;

    @Bean(name = "queryRunner")
    public QueryRunner queryRunner(){
        // 使用SQlServer数据库时，pmdKnownBroken要设置为true，否则在预编译时的参数无法自动传入
        return  new QueryRunner(dataSource,true);
    }
}
