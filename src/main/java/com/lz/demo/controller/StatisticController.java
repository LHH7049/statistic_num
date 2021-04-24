package com.lz.demo.controller;

import com.lz.demo.data.AnalysisVo;
import com.lz.demo.data.Result;
import com.lz.demo.data.StatisticNumDo;
import com.lz.demo.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: lz
 * @Date: 2021/4/20 13:54
 */
@RequestMapping("/statistic")
@RestController
public class StatisticController {

    @Autowired
    private StatisticService service;

    @RequestMapping("/show.json")
    @Cacheable(cacheNames = "statistic", key = "#keyStr")
    public Result<AnalysisVo> show(@RequestParam String keyStr){
        String[] split = keyStr.split(",");
        return service.show(buildKeyArr(split));
    }

    private List<Integer> buildKeyArr(String[] keyStr){
        List<Integer> keyArr = new ArrayList<>();
        for (String s : keyStr) {
            if (StringUtils.hasText(s)){
                keyArr.add(Integer.valueOf(s));
            }
        }

        return keyArr;
    }

    @RequestMapping("/add.json")
    @CacheEvict(cacheNames = "statistic", allEntries = true)
    public Result<Void> add(@RequestParam String param){
        return service.add(param);
    }

    @RequestMapping("/getAll.json")
    @Cacheable(cacheNames = "statistic")
    public Result<List<StatisticNumDo>> getAll(){
        return service.getAll();
    }

    @RequestMapping("/delete.json")
    @CacheEvict(cacheNames = "statistic", allEntries = true)
    public Result<Void> delete(@RequestParam int id){
        return service.delete(id);
    }

    @RequestMapping("/forceReload.json")
    @CacheEvict(cacheNames = "statistic", allEntries = true)
    public Result<Void> forceReload(){
        return service.forceReload();
    }
}
