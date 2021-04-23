package com.lz.demo.data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: lz
 * @Date: 2021/4/22 14:19
 */
public class Constant {

    public static final int FIVE_COMBINATION = 5;
    public static final int FOUR_COMBINATION = 4;
    public static final int THREE_COMBINATION = 3;

    public static final List<Integer> combinationNums = new ArrayList<>();
    static {
        combinationNums.add(THREE_COMBINATION);
        combinationNums.add(FOUR_COMBINATION);
        combinationNums.add(FIVE_COMBINATION);
    }
}
