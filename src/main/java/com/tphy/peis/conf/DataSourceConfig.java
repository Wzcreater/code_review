package com.tphy.peis.conf;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    //@primary默认数据源
    @Primary
    @Bean(name = "primaryDataSource")
    //指定数据读取配置的前缀
    @ConfigurationProperties(prefix = "custom.datasource.ds1")
    public DataSource primaryDataSource(){
        return DataSourceBuilder.create().build() ;
    }

    @Bean(name = "secondaryDataSource")
    @ConfigurationProperties(prefix = "custom.datasource.ds2")
    public DataSource secondaryDataSource(){
        return DataSourceBuilder.create().build() ;
    }

}
