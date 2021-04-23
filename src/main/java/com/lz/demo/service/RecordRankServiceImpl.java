package com.lz.demo.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: lz
 * @Date: 2021/4/22 11:28
 */
public class RecordRankServiceImpl implements IRecordRankService{
    private final Map<String, Integer> recordMap = new ConcurrentHashMap<>();

    @Override
    public Map<String, Integer> getRecordMap() {
        return recordMap;
    }

    @Override
    public void record(String key) {
        recordMap.compute(key, (k, v) -> v == null ? 1 : v + 1);
    }
}
