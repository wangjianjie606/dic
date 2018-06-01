package com.spider.dic.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="word")
public class Word {

    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid") //这个是hibernate的注解
    @GeneratedValue(generator="idGenerator") //使用uuid的生成策略
    private String id;

    @Basic
    @Column(name="word",unique = true,nullable = false)
    private String word;

    @OneToMany(mappedBy = "enSymbolWord", fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
    private Set<Symbol> enSymbols;
    @OneToMany(mappedBy = "amSymbolWord", fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
    private Set<Symbol> amSymbols;
    @OneToMany(mappedBy = "word", fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
    private Set<Mean> means;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Set<Symbol> getEnSymbols() {
        return enSymbols;
    }

    public void setEnSymbols(Set<Symbol> enSymbols) {
        this.enSymbols = enSymbols;
    }

    public Set<Symbol> getAmSymbols() {
        return amSymbols;
    }

    public void setAmSymbols(Set<Symbol> amSymbols) {
        this.amSymbols = amSymbols;
    }

    public Set<Mean> getMeans() {
        return means;
    }

    public void setMeans(Set<Mean> means) {
        this.means = means;
    }

    public Word(String word) {
        this.word = word;
    }

    public Word() {
    }
}
