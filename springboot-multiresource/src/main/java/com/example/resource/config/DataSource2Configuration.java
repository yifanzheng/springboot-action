package com.example.resource.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 数据源2配置
 *
 * @author kevin
 **/
@Configuration
@MapperScan(basePackages = "com.example.resource.datasource02", sqlSessionFactoryRef = "sqlSessionFactory2")
public class DataSource2Configuration {

    /**
     * 配置数据源
     *
     * @return
     */
    @Bean(name = "datasource2")
    @ConfigurationProperties(prefix = "spring.datasource.resource2")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 配置sql会话工厂
     *
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "sqlSessionFactory2")
    public SqlSessionFactory sessionFactory(@Qualifier("datasource2") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        // 加载mapper配置文件
        // bean.setMapperLocations(
        // new
        // PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/test2/*.xml"));

        return bean.getObject();
    }

    /**
     * 事务管理器
     *
     * @param dataSource
     * @return
     */
    @Bean(name = "transactionManager2")
    public DataSourceTransactionManager transactionManager(@Qualifier("datasource2") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "SqlSessionTemplate2")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory2") SqlSessionFactory sessionFactory) {
        return new SqlSessionTemplate(sessionFactory);
    }
}
