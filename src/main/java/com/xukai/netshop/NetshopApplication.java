package com.xukai.netshop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan(basePackages = "com.xukai.netshop.dataobject.mapper")
@EnableCaching
public class NetshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetshopApplication.class, args);
    }
}
