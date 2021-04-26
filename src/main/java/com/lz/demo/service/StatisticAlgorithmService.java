package com.lz.demo.service;


import com.lz.demo.data.Constant;
import com.lz.demo.data.Result;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: lz
 * @Date: 2021/4/19 9:33
 */
public class StatisticAlgorithmService {

    private static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private static final int INITIAL_COUNT = 0;
    private static final AtomicInteger count = new AtomicInteger(INITIAL_COUNT);
    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(CORE_POOL_SIZE, CORE_POOL_SIZE, 1000, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());

    private Map<Integer, IRecordRankService> recordRankService;

    public StatisticAlgorithmService(Map<Integer, IRecordRankService> recordRankService) {
        this.recordRankService = recordRankService;
    }

    public void destroy(){
        System.out.println("关闭线程");
        shutdownExecutor();
    }

    public Result<Void> run(List<int[]> inputData, List<Integer> inputKeys) {
        if (checkParamEmpty(inputData, inputKeys)) {
            return Result.errorResult("有数据为空");
        }

        for (int[] inputDatum : inputData) {
            count.addAndGet(1);
            executor.execute(new StatisticRunner(inputDatum, inputKeys, new ArrayList<>()));
        }

        waitSubTaskFinish();
        return Result.successResult(null);
    }

    private boolean checkParamEmpty(List<int[]> data, List<Integer> keys) {
        return data == null || data.size() == 0 || keys == null || keys.size() == 0 || recordRankService == null;
    }

    private void waitSubTaskFinish() {
        while (count.get() > INITIAL_COUNT) {
        }
    }

    private void shutdownExecutor(){
        executor.shutdown();
        while(!executor.isTerminated()){}
    }

    class StatisticRunner implements Runnable {
        private int[] items;
        private List<Integer> inputKeys;
        private List<Integer> combination;

        public StatisticRunner(int[] items, List<Integer> inputKeys, List<Integer> combination) {
            this.items = items;
            this.inputKeys = inputKeys;
            this.combination = combination;
        }

        @Override
        public void run() {
            int step = 0;
            int size = combination.size();
            if (size != 0) {
                step = combination.get(size - 1);
                if (step >= items.length || size >= Constant.FIVE_COMBINATION - inputKeys.size()) {
                    count.addAndGet(-1);
                    return;
                }
            }
            for (int i = step + 1; i < items.length; i++) {
                if (items[i] >= 1 && !inputKeys.contains(i)) {
                    List<Integer> newCombination = new ArrayList<>(combination);
                    newCombination.add(i);
                    count.addAndGet(1);
                    executor.execute(new StatisticRunner(items, inputKeys, newCombination));

                    IRecordRankService rankService = recordRankService.get(newCombination.size() + inputKeys.size());
                    if (rankService == null) {
                        continue;
                    }
                    String key = getKey(inputKeys, newCombination);
                    rankService.record(key);
                }
            }
            count.addAndGet(-1);
        }

        private String getKey(List<Integer> keys, List<Integer> waitItems) {
            List<Integer> item = new ArrayList<>(keys);
            item.addAll(waitItems);
            StringBuilder res = new StringBuilder();
            item.forEach(value -> res.append(value).append(","));
            res.deleteCharAt(res.length()-1);
            return res.toString();
        }
    }
}
