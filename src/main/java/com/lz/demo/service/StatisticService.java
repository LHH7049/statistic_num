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

    private static int INIT_LENGTH = 81;
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
        if (recordServiceMap != null){
            return ResultUtil.buildSuccessResult(buildAnalysisVo());
        } else {
            reload();
        }

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
            if (entry.getValue() >= 2) {
                keyRanks.add(new KeyRank(entry.getKey(), entry.getValue()));
            }
        }
        keyRanks.sort((k1, k2) -> k2.getCount() - k1.getCount());
        return keyRanks;
    }

    @CacheEvict("statistic")
    public Result<Void> delete(int id){
        clear();
        statisticMapper.delete(id);
        return ResultUtil.buildSuccessResult();
    }

    @CacheEvict("statistic")
    public Result<Void> add(String param){
        clear();
        statisticMapper.add(new StatisticNumDo(-1, param));
        return ResultUtil.buildSuccessResult();
    }

    @Cacheable("statistic")
    public Result<List<StatisticNumDo>> getAll(){
        List<StatisticNumDo> result = statisticMapper.getAll();
        return ResultUtil.buildSuccessResult(result);
    }

    @CacheEvict("statistic")
    public Result<Void> forceReload(){
        clear();
        return ResultUtil.buildSuccessResult();
    }



    //        List<int[]> data = new ArrayList<>();
//        // 1,2,3,4,5,6,7
//        data.add(new int[]{0,1,1,1,1,1,1,1});
//        // 3,4,5,6,7,8,9
//        data.add(new int[]{0,0,0,1,1,1,1,1,1,1});
//        // 5,6,7,8,9,10,11
//        data.add(new int[]{0,0,0,0,0,1,1,1,1,1,1,1});


//    public Result<AnalysisVo> show(MultipartFile file, List<Integer> keys){
//        List<int[]> data = null;
//        List<KeyRank> keyRank = null;
//        try {
//            // 从File中读取数据
//            data = getDataFromExcel(file);
//            // 解析数据
//            Map<String, Integer> result = new StatisticAlgorithmService(data, keys).run();
//            keyRank = getKeyRank(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return ResultUtil.buildSuccessResult(new AnalysisVo(data, keyRank));
//    }

//    private List<int[]> getDataFromExcel(MultipartFile file) throws Exception {
//        List<List<String>> readData = ExcelUtil.INSTANCE.read(file, 0);
//        List<int[]> result = new ArrayList<>();
//        for (List<String> readDatum : readData) {
//            int[] item = new int[readDatum.size()];
//            int index = 0;
//            for (String s : readDatum) {
//                if (StringUtils.hasText(s)){
//                    item[index++] = Integer.parseInt(s);
//                }
//            }
//            result.add(item);
//        }
//        return result;
//    }


}
