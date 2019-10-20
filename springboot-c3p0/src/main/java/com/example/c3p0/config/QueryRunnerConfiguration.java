package com.example.c3p0.config;

import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * QueryRunner 配置类
 *
 * @author star
 **/
@Configuration
public class QueryRunnerConfiguration {

    @Autowired
    private DataSource dataSource;

    @Bean(name = "queryRunner")
    public QueryRunner queryRunner(){
        // 使用 SQlServer 数据库时，pmdKnownBroken 要设置为 true，否则在预编译时的参数无法自动传入
        return new QueryRunner(dataSource,true);
    }
}
