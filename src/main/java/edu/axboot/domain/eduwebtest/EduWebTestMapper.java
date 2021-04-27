package edu.axboot.domain.eduwebtest;

    import com.chequer.axboot.core.mybatis.MyBatisMapper;
    import java.util.List;

    public interface EduWebTestMapper extends MyBatisMapper {
        List<EduWebTest>myBatisSelect(EduWebTest eduWebTest);

       /* int update(EduWebTest eduWebTest);
        int delete(EduWebTest eduWebTest);
        int insert(EduWebTest eduWebTest);*/

}
