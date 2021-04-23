package com.lz.demo.util;

import com.lz.demo.data.Result;

/**
 * @Author: lz
 * @Date: 2021/4/20 14:06
 */
public class ResultUtil {

    public static <T> Result<T> buildSuccessResult(){
        return new Result<T>().successResult();
    }

    public static <T> Result<T> buildSuccessResult(T value){
        return Result.successResult(value);
    }

    public static <T> Result<T> buildErrorResult(String msg){
        return Result.errorResult(msg);
    }
}
