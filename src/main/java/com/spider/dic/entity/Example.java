package com.spider.dic.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="example", schema ="dic")
public class Example {

    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid") //这个是hibernate的注解
    @GeneratedValue(generator="idGenerator") //使用uuid的生成策略
    private String id;
    @Basic
    @Column(name="ex",columnDefinition = "Text")
    private String ex;
    @Basic
    @Column(name="tran",columnDefinition = "Text")
    private String tran;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "mean", referencedColumnName = "id")
    private Mean mean;

    public Mean getMean() {
        return mean;
    }

    public void setMean(Mean mean) {
        this.mean = mean;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEx() {
        return ex;
    }

    public void setEx(String ex) {
        this.ex = ex;
    }

    public String getTran() {
        return tran;
    }

    public void setTran(String tran) {
        this.tran = tran;
    }
}
