package com.spider.dic.dao;

import com.spider.dic.entity.UnCatchWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UnCatchWordDao extends JpaRepository<UnCatchWord, String> {
    boolean existsByWord(String word);

    UnCatchWord findUnCatchWordByWord(String word);

    List<UnCatchWord> findFirst10ByState(String state);

//    @Query("SELECT u FROM UnCatchWord u WHERE u.state='Y' ")
//    List<UnCatchWord> findWords();
}
