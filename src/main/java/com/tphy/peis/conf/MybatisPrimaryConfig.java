package com.tphy.peis.conf;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"com.tphy.peis.mapper.peisReport"}, sqlSessionFactoryRef = "sqlSessionFactoryPrimary")
public class MybatisPrimaryConfig {

    @Autowired
    /**
     * Spring的Bean注入配置注解，该注解指定注入的Bean的名称，
     * Spring框架使用byName方式寻找合格的bean，
     * 这样就消除了byType方式产生的歧义
     */
    @Qualifier("primaryDataSource")
    private DataSource primaryDataSource ;
    @Primary //默认SqlSessionFactory
    @Bean
    public SqlSessionFactory sqlSessionFactoryPrimary() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean() ;
        factoryBean.setDataSource(primaryDataSource);//设置数据源
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/peisReport/*Mapper.xml"));
        return factoryBean.getObject() ;

    }

    @Primary
    @Bean
    public SqlSessionTemplate sqlSessionTemplatePrimary() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactoryPrimary()) ;
        return template ;
    }
}
