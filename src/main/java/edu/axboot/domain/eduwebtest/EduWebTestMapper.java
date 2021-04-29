package edu.axboot.domain.eduwebtest;

import com.chequer.axboot.core.mybatis.MyBatisMapper;

import java.util.HashMap;
import java.util.List;

public interface EduWebTestMapper extends MyBatisMapper {
    // List<EduWebTest>myBatisSelect(EduWebTest eduWebTest);
    List<EduWebTest>select(HashMap<String, String> params);//Hashmap,map 객체로 보내는 것이 아니라 map형태로 보냄
    EduWebTest selectOne(Long id);
    int insert(EduWebTest eduWebTest);
    int update(EduWebTest eduWebTest);
    int delete(long id);


}
