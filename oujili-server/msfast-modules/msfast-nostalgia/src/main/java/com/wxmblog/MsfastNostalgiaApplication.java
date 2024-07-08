package com.wxmblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ServletComponentScan
@EnableRetry
@EnableAsync
public class MsfastNostalgiaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsfastNostalgiaApplication.class, args);
    }

}
