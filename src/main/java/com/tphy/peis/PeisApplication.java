package com.tphy.peis;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@Slf4j
@MapperScan(basePackages = "com.tphy.peis.mapper")
public class PeisApplication {

    public static void main(String[] args) {
        SpringApplication.run(PeisApplication.class, args);
        log.info("********体检PEIS系统启动成功********");
    }

}
