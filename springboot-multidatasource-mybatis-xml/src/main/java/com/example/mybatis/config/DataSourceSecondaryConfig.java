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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * DataSourceSecondaryConfig
 *
 * @author star
 */
@Configuration
@MapperScan(basePackages = "com.example.mybatis.mapper.secondary", sqlSessionTemplateRef = "SecondarySqlSessionTemplate")
public class DataSourceSecondaryConfig {

    @Bean(name = "SecondaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "SecondarySqlSessionFactory")
    public SqlSessionFactory sqlSessionFactorySecondary(@Qualifier("SecondaryDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mybatis/mapper/secondary/*.xml"));

        return factoryBean.getObject();

    }

    @Bean(name = "SecondaryTransactionManager")
    public DataSourceTransactionManager transactionManagerSecondary(@Qualifier("SecondaryDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "SecondarySqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplateSecondary(@Qualifier("SecondarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
