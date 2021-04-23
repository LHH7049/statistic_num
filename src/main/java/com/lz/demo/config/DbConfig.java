package com.lz.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @Author: lz
 * @Date: 2021/4/22 15:59
 */
@Configuration
public class DbConfig {

    @Bean
    @ConfigurationProperties(prefix = "db")
    public DataSource dataSource(){
        return new DruidDataSource();
    }
}
