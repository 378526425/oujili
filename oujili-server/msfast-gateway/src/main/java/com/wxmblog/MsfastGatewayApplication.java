package com.wxmblog;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.wxmblog.base.common.aspect.ViolationAspect;
import com.wxmblog.base.common.config.RedissonAutoConfig;
import com.wxmblog.base.common.config.SwaggerConfig;
import com.wxmblog.base.common.handler.ServiceBlockExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DruidDataSourceAutoConfigure.class})
@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {ServiceBlockExceptionHandler.class
        , SwaggerConfig.class
        , RedissonAutoConfig.class,
        ViolationAspect.class
})})
public class MsfastGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsfastGatewayApplication.class, args);
    }

}
