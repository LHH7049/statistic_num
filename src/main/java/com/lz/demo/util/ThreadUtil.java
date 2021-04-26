package com.lz.demo.util;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: lz
 * @Date: 2021/4/26 18:26
 */
public class ThreadUtil {

    public static final ThreadUtil INSTANCE = new ThreadUtil();

    private static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private static final int ALIVE_TIME = 1000;
    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(CORE_POOL_SIZE, CORE_POOL_SIZE, ALIVE_TIME, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());

    private ThreadUtil() {
    }

    public void run(Runnable task){
        executor.execute(task);
    }

    public void destroy(){
        System.out.println("关闭线程池");
        executor.shutdown();
        while(!executor.isTerminated()){}
    }
}
