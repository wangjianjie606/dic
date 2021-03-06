package com.spider.dic.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.spider.dic.dao.UnCatchWordDao;
import com.spider.dic.dao.WordDao;
import com.spider.dic.entity.*;
import com.spider.dic.vo.ExampleVO;
import com.spider.dic.vo.MeanVO;
import com.spider.dic.vo.SymbolVO;
import com.spider.dic.vo.WordVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SpiderService {

    @Autowired
    private WordDao wordDao;
    @Autowired
    private UnCatchWordDao unCatchWordDao;

    public void setUnCatchWordState(String word,String state){
        UnCatchWord w = unCatchWordDao.findUnCatchWordByWord(word);
        w.setState(state);
        unCatchWordDao.save(w);
    }

    public void saveWord(String str, JSONObject jsonObject){
        Set<String> unCatchWord = new HashSet<>();
        Word word = new Word(str);
        Set<Mean> means = getMeans(jsonObject,word,unCatchWord);

        word.setMeans(means);
        if(means==null||means.size()<1) {
            this.setUnCatchWordState(str,"N");
            return;
        }

        JSONObject symbols ;
        try{
            symbols = jsonObject.getJSONObject("baesInfo")
                    .getJSONArray("symbols")
                    .getJSONObject(0);
        }catch (NullPointerException e){
            this.setUnCatchWordState(str,"N");
            System.out.println("=====================================>未查询到该单词的意思");
            return;
        }
        Set<Symbol> enSymbol = getSymbol(symbols.getString("ph_en"),word,true);
        Set<Symbol> amSymbol = getSymbol(symbols.getString("ph_am"),word,false);
        word.setAmSymbols(amSymbol);
        word.setEnSymbols(enSymbol);

        wordDao.save(word);

        saveUnCatchWord(unCatchWord);

        UnCatchWord w = unCatchWordDao.findUnCatchWordByWord(str);
        unCatchWordDao.delete(w.getId());
    }

    public void saveUnCatchWord(String unCatchWord){
        Set<String> unCatchWrods = new HashSet<>();
        setUnCatchWord(unCatchWord,unCatchWrods);
        this.saveUnCatchWord(unCatchWrods);
    }

    public WordVO findWord(String word){
        WordVO ww = new WordVO();
        Word w = wordDao.findWordByWordEquals(word);
        BeanUtils.copyProperties(w,ww,"enSymbols","amSymbols","means");
        Set<SymbolVO> enSymbols = new HashSet<>();
        Set<SymbolVO> amSymbols = new HashSet<>();
        Set<MeanVO> means = new HashSet<>();
        w.getAmSymbols().forEach(item->{
            SymbolVO vo = new SymbolVO();
            BeanUtils.copyProperties(item, vo);
            amSymbols.add(vo);
        });
        w.getEnSymbols().forEach(item->{
            SymbolVO vo = new SymbolVO();
            BeanUtils.copyProperties(item, vo);
            enSymbols.add(vo);
        });
        w.getMeans().forEach(item->{
            MeanVO vo = new MeanVO();
            BeanUtils.copyProperties(item, vo, "ex");
            Set<ExampleVO> ex = new HashSet<>();
            item.getEx().forEach(ite->{
                ExampleVO v = new ExampleVO();
                BeanUtils.copyProperties(ite,v);
                ex.add(v);
            });
            vo.setEx(ex);
            means.add(vo);
        });
        ww.setAmSymbols(amSymbols);
        ww.setEnSymbols(enSymbols);
        ww.setMeans(means);

        return ww;
    }

    private void saveUnCatchWord(Set<String> unCatchWord ){
        List<UnCatchWord> list = new ArrayList<>();
        for (String word: unCatchWord) {
            if(!wordDao.existsByWord(word) &&
                    !unCatchWordDao.existsByWord(word)){
                UnCatchWord w = new UnCatchWord();
                w.setWord(word);
                w.setState("Y");
                list.add(w);
            }
        }
        unCatchWordDao.save(list);
    }

    private Set<Symbol> getSymbol(String str,Word word,boolean b){
        Set<Symbol> symbols = new HashSet<>();
        String[] ss = str.split(",");
        for (String s: ss) {
            Symbol symbol = new Symbol(s);
            symbols.add(symbol);
            if(b)
                symbol.setEnSymbolWord(word);
            else
                symbol.setAmSymbolWord(word);
        }
        return symbols;
    }

    private Set<Mean> getMeans(JSONObject jsonObject, Word word, Set<String> unCatchWord) {
        Set<Mean> means = new HashSet<>();
        JSONArray jj = null;
        try {
            jj = jsonObject.getJSONArray("collins")
                    .getJSONObject(0)
                    .getJSONArray("entry");
        }catch (NullPointerException e){
            System.out.println("=====================================>未查询到该单词的意思");
//            e.printStackTrace();
            return null;
        }

        for (Object obj : jj) {
            JSONObject jsonMean = (JSONObject) obj;

            Mean mean = new Mean();
            mean.setMean(jsonMean.getString("def"));
            mean.setTran(jsonMean.getString("tran"));
            mean.setPosp(jsonMean.getString("posp"));
            means.add(mean);
            Set<Example> examples = new HashSet<>();
            mean.setEx(examples);
            mean.setWord(word);

            JSONArray jsonExamples = jsonMean.getJSONArray("example");
            for (Object j: jsonExamples) {
                JSONObject jsonExample = (JSONObject)j;
                Example example = new Example();
                example.setEx(jsonExample.getString("ex"));
                example.setTran(jsonExample.getString("tran"));
                examples.add(example);
                example.setMean(mean);

                setUnCatchWord(example.getEx(),unCatchWord);
            }
        }
        return means;
    }

    private void setUnCatchWord(String line, Set<String> unCatchWord){
        String newLine = line.replaceAll(","," ").replaceAll("[^a-zA-Z,\\r]"," ").toLowerCase();
        String[] ss = newLine.split(" ");
        for (String s: ss) {
            if(!StringUtils.isEmpty(s)) unCatchWord.add(s);
        }
    }

    public List<String> findUnCatchWordList(){
        List<String> list = new ArrayList<>();
        List<UnCatchWord> ll = unCatchWordDao.findFirst10ByState("Y");
        for (UnCatchWord u : ll) {
            list.add(u.getWord());
        }

        return list;
    }


    public static void main(String[] args) {

        String yuanlaiString = "Contents, " + "\n" +
                "Preface " +"\n" +
                "Chapter 1: Getting started with the ASP.NET MVC Framework" ;
        System.out.println("原来的：");
        System.out.println(yuanlaiString);

        System.out.println("替换过后的：");
        String newString = yuanlaiString.replaceAll(","," ").replaceAll("[^a-zA-Z,\\r]", " ").toLowerCase();
        System.out.print(newString);
        String[] s = newString.split(" ");


    }
}
