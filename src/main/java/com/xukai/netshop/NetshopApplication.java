package com.xukai.netshop;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@Import(FdfsClientConfig.class)
@EnableScheduling
public class NetshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetshopApplication.class, args);
    }

}
