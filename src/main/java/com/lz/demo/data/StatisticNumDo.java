package com.lz.demo.data;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @Author: lz
 * @Date: 2021/4/22 13:48
 */
@Component("statisticNumDo")
public class StatisticNumDo implements Serializable {
    private int id;
    private String numArrStr;

    public StatisticNumDo() {
    }

    public StatisticNumDo(int id, String numArrStr) {
        this.id = id;
        this.numArrStr = numArrStr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumArrStr() {
        return numArrStr;
    }

    public void setArrStr(String arrStr) {
        this.numArrStr = arrStr;
    }

    @Override
    public String toString() {
        return "StatisticNumDo{" +
                "id=" + id +
                ", numArrStr='" + numArrStr + '\'' +
                '}';
    }
}
