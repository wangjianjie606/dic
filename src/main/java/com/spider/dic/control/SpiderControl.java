package com.spider.dic.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.spider.dic.service.SpiderService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

@RestController
public class SpiderControl {

    @Autowired
    private SpiderService spiderService;

    private List<String> unCatchWordList = null;
    private Random rand = new Random();

    @RequestMapping(value = "/start" )
    public void startSpider(){
        spider();
    }


    private void spider(){
        while(unCatchWordList==null){
            this.unCatchWordList = spiderService.findUnCatchWordList();
            if(this.unCatchWordList==null||this.unCatchWordList.size()<1){
                break;
            }
            for (String word: unCatchWordList) {
                try {
                    Thread.sleep(500*rand.nextInt(10));
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                findWord(word);
            }
            this.unCatchWordList = null;
        }
    }

    private void findWord(String word){
        String url = "http://www.iciba.com/index.php?a=getWordMean&c=search&word="+word;
//        int offset = 0;
//        System.out.println("第"+offset+"次连接开始....");
        System.out.println("===================>查询单词:   "+word);
        Map<String, String> cookies = getCookies();

        Document doc = getConnect(url, cookies);
        if(doc==null){
            System.out.println("未获取到连接,等待1s");
            try {
                Thread.sleep(1000);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            return;
        }
        String text = doc.getElementsByTag("body").text();
        if(text!=null && text.length()>0){
            JSONObject jb = null;
            try {
                jb = JSON.parseObject(text);
            }catch (Exception e){
                spiderService.setUnCatchWordState(word,"E");
            }
            if(jb!=null){
                spiderService.saveWord(word, jb);
            }
        }
    }

    private Map<String, String> getCookies(){
        return new HashMap<>();
    }

    private Document getConnect(String url, Map<String,String> cookies){
        try {
            Connection conn = Jsoup.connect(url)
//                    .userAgent(getUserAgent())
                    .postDataCharset("utf-8");
//            conn.cookies(cookies);
            conn.header("charset","utf-8");
//            conn.header("Content-Type","application/json");
            //conn.header("Mimetype","application/json");
            return conn.ignoreContentType(true).get();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
