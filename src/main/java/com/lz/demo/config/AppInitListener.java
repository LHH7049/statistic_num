package com.lz.demo.config;

import com.lz.demo.util.ThreadUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @Author: lz
 * @Date: 2021/4/26 18:33
 */
public class AppInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ThreadUtil.INSTANCE.destroy();
    }
}
