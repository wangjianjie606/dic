package com.spider.dic.vo;

import java.util.Set;

public class MeanVO {

    private String tran;
    private String mean;
    private String posp;
    private Set<ExampleVO> ex;

    public String getTran() {
        return tran;
    }

    public void setTran(String tran) {
        this.tran = tran;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public String getPosp() {
        return posp;
    }

    public void setPosp(String posp) {
        this.posp = posp;
    }

    public Set<ExampleVO> getEx() {
        return ex;
    }

    public void setEx(Set<ExampleVO> ex) {
        this.ex = ex;
    }
}
