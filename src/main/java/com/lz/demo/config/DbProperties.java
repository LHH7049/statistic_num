package com.lz.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: lz
 * @Date: 2021/4/22 15:59
 */
public class DbProperties {
    private String url;
    private String driver;
    private String userName;
    private String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
