package edu.axboot.domain.eduwebtest;

import com.chequer.axboot.core.mybatis.MyBatisMapper;
import com.chequer.axboot.core.parameter.RequestParams;

import java.util.HashMap;
import java.util.List;

public interface EduWebTestMapper extends MyBatisMapper {
    List<EduWebTest>select(HashMap<String, String> params);
    EduWebTest selectOne(Long id);
    int insert(EduWebTest eduWebTest);
    int update(EduWebTest eduWebTest);
    int delete(long id);
    List<EduWebTest>errlog(HashMap<String, Object> params);

}
