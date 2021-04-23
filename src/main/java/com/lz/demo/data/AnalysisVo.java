package com.lz.demo.data;

import java.util.List;

/**
 * @Author: lz
 * @Date: 2021/4/20 14:30
 */
public class AnalysisVo {
    private List<KeyRank> threeRank;
    private List<KeyRank> fourRank;
    private List<KeyRank> fiveRank;

    public AnalysisVo() {
    }

    public AnalysisVo(List<KeyRank> threeRank, List<KeyRank> fourRank, List<KeyRank> fiveRank) {
        this.threeRank = threeRank;
        this.fourRank = fourRank;
        this.fiveRank = fiveRank;
    }

    public void build(int idx, List<KeyRank> keyRanks){
        switch (idx){
            case Constant.THREE_COMBINATION: this.setThreeRank(keyRanks);break;
            case Constant.FOUR_COMBINATION: this.setFourRank(keyRanks);break;
            case Constant.FIVE_COMBINATION: this.setFiveRank(keyRanks);break;
            default:break;
        }
    }

    public List<KeyRank> getThreeRank() {
        return threeRank;
    }

    public void setThreeRank(List<KeyRank> threeRank) {
        this.threeRank = threeRank;
    }

    public List<KeyRank> getFourRank() {
        return fourRank;
    }

    public void setFourRank(List<KeyRank> fourRank) {
        this.fourRank = fourRank;
    }

    public List<KeyRank> getFiveRank() {
        return fiveRank;
    }

    public void setFiveRank(List<KeyRank> fiveRank) {
        this.fiveRank = fiveRank;
    }
}
