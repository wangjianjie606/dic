package com.spider.dic.vo;

import java.util.Set;

public class WordVO {
    private String word;
    private Set<SymbolVO> enSymbols;
    private Set<SymbolVO> amSymbols;
    private Set<MeanVO> means;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Set<SymbolVO> getEnSymbols() {
        return enSymbols;
    }

    public void setEnSymbols(Set<SymbolVO> enSymbols) {
        this.enSymbols = enSymbols;
    }

    public Set<SymbolVO> getAmSymbols() {
        return amSymbols;
    }

    public void setAmSymbols(Set<SymbolVO> amSymbols) {
        this.amSymbols = amSymbols;
    }

    public Set<MeanVO> getMeans() {
        return means;
    }

    public void setMeans(Set<MeanVO> means) {
        this.means = means;
    }
}
