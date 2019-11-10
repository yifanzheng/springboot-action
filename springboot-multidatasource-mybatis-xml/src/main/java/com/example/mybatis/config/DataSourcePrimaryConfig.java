package com.example.mybatis.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * DataSourcePrimaryConfig
 *
 * @author star
 */
@Configuration
@MapperScan(basePackages = "com.example.mybatis.mapper.primary", sqlSessionTemplateRef = "PrimarySqlSessionTemplate")
public class DataSourcePrimaryConfig {

    @Bean(name = "PrimaryDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource dataSourcePrimary() {
       return new DruidDataSource();
    }

    @Bean(name = "PrimarySqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactoryPrimary(@Qualifier("PrimaryDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mybatis/mapper/primary/*.xml"));

        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "PrimaryTransactionManager")
    @Primary
    public DataSourceTransactionManager transactionManagerPrimary(@Qualifier("PrimaryDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "PrimarySqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplatePrimary(@Qualifier("PrimarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
