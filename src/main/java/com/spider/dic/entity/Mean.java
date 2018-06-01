package com.spider.dic.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="mean")
public class Mean {
    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid") //这个是hibernate的注解
    @GeneratedValue(generator="idGenerator") //使用uuid的生成策略
    private String id;
    @Basic
    @Column(name="tran")
    private String tran;
    @Basic
    @Column(name="mean")
    private String mean;
    @Basic
    @Column(name="posp")
    private String posp;

    @OneToMany(mappedBy = "mean", fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
    private Set<Example> ex;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "word", referencedColumnName = "id")
    private Word word;

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Set<Example> getEx() {
        return ex;
    }

    public void setEx(Set<Example> ex) {
        this.ex = ex;
    }
}
