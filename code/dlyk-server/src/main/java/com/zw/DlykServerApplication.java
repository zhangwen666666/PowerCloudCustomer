package com.zw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.zw.mapper")
@SpringBootApplication
@EnableScheduling
public class DlykServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DlykServerApplication.class, args);
    }

}
