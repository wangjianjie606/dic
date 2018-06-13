package com.spider.dic.dao;

import com.spider.dic.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WordDao extends JpaRepository<Word, String> {

    Word findWordByWordEquals(String word);

    boolean existsByWord(String word);

//    List<Word> f
}
