package com.spider.dic.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="symbol", schema ="dic")
public class Symbol {
    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid") //这个是hibernate的注解
    @GeneratedValue(generator="idGenerator") //使用uuid的生成策略
    private String id;
    @Basic
    @Column(name="symbol")
    private String symbol;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "enSymbolWord", referencedColumnName = "id")
    private Word enSymbolWord;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "amSymbolWord", referencedColumnName = "id")
    private Word amSymbolWord;

    public Word getAmSymbolWord() {
        return amSymbolWord;
    }

    public void setAmSymbolWord(Word amSymbolWord) {
        this.amSymbolWord = amSymbolWord;
    }

    public Word getEnSymbolWord() {
        return enSymbolWord;
    }

    public void setEnSymbolWord(Word enSymbolWord) {
        this.enSymbolWord = enSymbolWord;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Symbol() {
    }

    public Symbol(String symbol) {
        this.symbol = symbol;
    }
}
