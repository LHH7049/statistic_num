package com.lz.demo.data;

/**
 * @Author: lz
 * @Date: 2021/4/20 14:06
 */
public class Result <T> {
    private int code;
    private String msg;
    private T value;

    public static final int SUCCESS = 0;
    public static final int ERROR = -1;


    public Result() {
    }

    public Result(int code, String msg, T value) {
        this.code = code;
        this.msg = msg;
        this.value = value;
    }

    public boolean isSuccess(){
        return getCode() == SUCCESS;
    }

    public Result<T> errorResult(){
        this.code = ERROR;
        this.msg = "操作失败";
        return this;
    }

    public Result<T> successResult(){
        this.code = SUCCESS;
        this.msg = "操作成功";
        return this;
    }

    public static <T> Result<T> successResult(T value){
        return new Result<T>().successResult().setValue(value);
    }

    public static <T> Result<T> errorResult(String msg){
        return new Result<T>().errorResult().setMsg(msg);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public Result<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getValue() {
        return value;
    }

    public Result<T> setValue(T value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", value=" + value +
                '}';
    }
}
