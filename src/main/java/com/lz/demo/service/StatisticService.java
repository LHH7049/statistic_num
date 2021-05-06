package com.lz.demo.service;

import com.google.gson.Gson;
import com.lz.demo.dao.StatisticMapper;
import com.lz.demo.data.*;
import com.lz.demo.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @Author: lz
 * @Date: 2021/4/20 14:10
 */
@Service
@EnableCaching
public class StatisticService {

    @Autowired
    private StatisticMapper statisticMapper;

    private static final int INIT_LENGTH = 81;
    private static final int MAX_LENGTH = 100;
    private static Map<Integer, IRecordRankService> recordServiceMap = null;

    private void reload(){
        recordServiceMap = new HashMap<>();
        for (Integer combinationNum : Constant.combinationNums) {
            recordServiceMap.put(combinationNum, new RecordRankServiceImpl());
        }
    }

    private void clear(){
        recordServiceMap = null;
    }

    public Result<AnalysisVo> show(List<Integer> keys){
        reload();

        List<int[]> data = readFromDB();
        Result<Void> result = runAlgorithm(data, keys);
        if (!result.isSuccess()) {
            return ResultUtil.buildErrorResult(result.getMsg());
        }
        return ResultUtil.buildSuccessResult(buildAnalysisVo());
    }

    private List<int[]> readFromDB(){
        Result<List<StatisticNumDo>> dataResult = getAll();
        if (!dataResult.isSuccess()){
            return new ArrayList<>();
        }
        List<int[]> result = new ArrayList<>();
        List<StatisticNumDo> numDos = dataResult.getValue();
        for (StatisticNumDo numDo : numDos) {
            String[] splitItem = numDo.getNumArrStr().split(",");
            int[] intItem = new int[INIT_LENGTH];
            for (String s : splitItem) {
                if (StringUtils.hasText(s)){
                    intItem[Integer.parseInt(s)] ++;
                }
            }
            result.add(intItem);
        }
        return result;
    }

    private Result<Void> runAlgorithm(List<int[]> data, List<Integer> keys){
        return new StatisticAlgorithmService(recordServiceMap).run(data, keys);
    }

    private AnalysisVo buildAnalysisVo(){
        AnalysisVo analysisVo = new AnalysisVo();
        for (Integer combinationNum : Constant.combinationNums) {
            List<KeyRank> keyRank = getKeyRank(recordServiceMap.get(combinationNum).getRecordMap());
            analysisVo.build(combinationNum, keyRank);
        }

        return analysisVo;
    }

    private List<KeyRank> getKeyRank(Map<String, Integer> keyMap){
        if (keyMap == null){
            return new ArrayList<>();
        }
        List<KeyRank> keyRanks = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : keyMap.entrySet()) {
            keyRanks.add(new KeyRank(entry.getKey(), entry.getValue()));
        }
        keyRanks.sort((k1, k2) -> k2.getCount() - k1.getCount());
        return keyRanks.subList(0, Math.min(keyRanks.size(), MAX_LENGTH));
    }

    public Result<Void> delete(int id){
        clear();
        statisticMapper.delete(id);
        return ResultUtil.buildSuccessResult();
    }

    public Result<Void> add(String param){
        clear();
        statisticMapper.add(new StatisticNumDo(-1, param));
        return ResultUtil.buildSuccessResult();
    }

    public Result<List<StatisticNumDo>> getAll(){
        List<StatisticNumDo> result = statisticMapper.getAll();
        return ResultUtil.buildSuccessResult(result);
    }

    public Result<Void> forceReload(){
        clear();
        return ResultUtil.buildSuccessResult();
    }

}
