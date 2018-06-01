package com.spider.dic.dao;

import com.spider.dic.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordDao extends JpaRepository<Word, String> {
    Word findWordByWordEquals(String word);

    boolean existsByWord(String word);
}
