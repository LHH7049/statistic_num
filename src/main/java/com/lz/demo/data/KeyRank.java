package com.lz.demo.data;

/**
 * @Author: lz
 * @Date: 2021/4/22 11:25
 */
public class KeyRank {
    private String key;
    private int count;
    private String ratio;

    public KeyRank() {
    }

    public KeyRank(String key, int count) {
        this.key = key;
        this.count = count;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "KeyRank{" +
                "key=" + key +
                ", num=" + count +
                '}';
    }
}
