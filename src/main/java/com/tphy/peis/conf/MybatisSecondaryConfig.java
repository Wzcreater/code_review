package com.tphy.peis.conf;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
//指定com.learn.ling.mapper.secondary下的mapper使用当前数据源
@MapperScan(basePackages = {"com.tphy.peis.mapper.pacsReport"}, sqlSessionFactoryRef = "sqlSessionFactorySecondary")
public class MybatisSecondaryConfig {

    @Autowired
    @Qualifier("secondaryDataSource")
    private DataSource secondaryDataSource ;

    @Bean
    public SqlSessionFactory sqlSessionFactorySecondary() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean() ;
        factoryBean.setDataSource(secondaryDataSource);//设置数据源
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/pacsReport/*Mapper.xml"));
        return factoryBean.getObject() ;

    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplateSecondary() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactorySecondary()) ;
        return template ;
    }
}
