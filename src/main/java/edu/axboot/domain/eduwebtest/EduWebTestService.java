package edu.axboot.domain.eduwebtest;

import com.querydsl.core.BooleanBuilder;
import org.springframework.stereotype.Service;
import edu.axboot.domain.BaseService;
import javax.inject.Inject;

import com.chequer.axboot.core.parameter.RequestParams;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EduWebTestService extends BaseService<EduWebTest, Long> {
    private EduWebTestRepository eduwebtestRepository;

    @Inject
    private EduWebTestMapper eduWebTestMapper;


    @Inject
    public EduWebTestService(EduWebTestRepository eduwebtestRepository) {
        super(eduwebtestRepository);
        this.eduwebtestRepository = eduwebtestRepository;
    }


    public List<EduWebTest> gets(RequestParams<EduWebTest> requestParams) {
        return findAll();
    }



    public List<EduWebTest> getByQeuryDsl (RequestParams<EduWebTest> requestParams) {
        String company = requestParams.getString("company","");
        String ceo = requestParams.getString("ceo", "");
        String bizno = requestParams.getString("bizno","");
        String useYn = requestParams.getString("useYn","");
        BooleanBuilder builder = new BooleanBuilder();

        if(isNotEmpty(company)){
            builder.and(qEduWebTest.companyNm.contains(company));
        }
        if (isNotEmpty(ceo)) {
            builder.and(qEduWebTest.ceo.contains(ceo));
        }
        if (isNotEmpty(bizno)) {
            builder.and(qEduWebTest.bizno.contains(bizno));
        }
        if (isNotEmpty(useYn)) {
            builder.and(qEduWebTest.useYn.eq(useYn));
        }
        List<EduWebTest> eduWebTestList = select()
                .from(qEduWebTest)
                .where(builder)
                .orderBy(qEduWebTest.companyNm.asc())
                .fetch();
        return eduWebTestList;
    }


    @Transactional
    public void saveByQuery(List<EduWebTest> request) {
        for(EduWebTest eduWebTest: request){
            if(eduWebTest.isCreated()){
                save(eduWebTest);
            }else if(eduWebTest.isModified()){
                update(qEduWebTest)
                        .set(qEduWebTest.companyNm, eduWebTest.getCompanyNm())
                        .set(qEduWebTest.ceo, eduWebTest.getCeo())
                        .set(qEduWebTest.bizno, eduWebTest.getBizno())
                        .set(qEduWebTest.tel, eduWebTest.getTel())
                        .set(qEduWebTest.email, eduWebTest.getEmail())
                        .set(qEduWebTest.useYn, eduWebTest.getUseYn())
                        .where(qEduWebTest.id.eq(eduWebTest.getId()))
                        .execute();
            }else if(eduWebTest.isDeleted()){
                delete(qEduWebTest)
                        .where(qEduWebTest.id.eq(eduWebTest.getId()))
                        .execute();
            }
        }

    }



    public EduWebTest  getByOne(long id) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qEduWebTest.id.eq(id));
        EduWebTest eduWebTest = select().from(qEduWebTest).where(builder).fetchOne();
        return eduWebTest;
    }


    public List<EduWebTest> myBatisSelect(RequestParams<EduWebTest> requestParams) {
        EduWebTest eduWebTest = new EduWebTest();
        eduWebTest.setCompanyNm(requestParams.getString("company", ""));
        eduWebTest.setCeo(requestParams.getString("ceo", ""));
        eduWebTest.setBizno(requestParams.getString("bizno", ""));
        List<EduWebTest>eduWebTestList = this.eduWebTestMapper.myBatisSelect(eduWebTest);
        return eduWebTestList;

    }



}
