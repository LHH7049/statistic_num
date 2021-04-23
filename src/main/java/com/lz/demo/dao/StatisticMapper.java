package com.lz.demo.dao;

import com.lz.demo.data.StatisticNumDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: lz
 * @Date: 2021/4/22 18:23
 */
@Mapper
public interface StatisticMapper {
    List<StatisticNumDo> getAll();

    void delete(int id);

    void add(StatisticNumDo numDo);
}
