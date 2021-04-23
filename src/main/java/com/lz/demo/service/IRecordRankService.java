package com.lz.demo.service;

import java.util.Map;

/**
 * @Author: lz
 * @Date: 2021/4/22 11:31
 */
public interface IRecordRankService {
    void record(String key);

    Map<String, Integer> getRecordMap();
}
