package edu.axboot.domain.eduwebtest;

import com.querydsl.core.BooleanBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import edu.axboot.domain.BaseService;

import javax.inject.Inject;

import com.chequer.axboot.core.parameter.RequestParams;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
public class EduWebTestService extends BaseService<EduWebTest, Long> {
    private static final Logger logger = LoggerFactory.getLogger(EduWebTestService.class);

    private EduWebTestRepository eduwebtestRepository;

    @Inject
    private EduWebTestMapper eduWebTestMapper;//mapper 사용한다고 정의


    @Inject
    public EduWebTestService(EduWebTestRepository eduwebtestRepository) {
        super(eduwebtestRepository);
        this.eduwebtestRepository = eduwebtestRepository;
    }

    //JPA
    public List<EduWebTest> gets(RequestParams<EduWebTest> requestParams) {
        List<EduWebTest> list2 = this.getFilter(findAll(), requestParams.getString("companyNm", ""), 1);
        List<EduWebTest> list3 = this.getFilter(list2, requestParams.getString("ceo", ""), 2);
        List<EduWebTest> list4 = this.getFilter(list3, requestParams.getString("bizno", ""), 3);
        List<EduWebTest> list5 = this.getFilter(list4, requestParams.getString("useYn", ""), 4);

        return list5;
    }

    private List<EduWebTest> getFilter(List<EduWebTest> sources, String filter, int type) {
        List<EduWebTest> targets = new ArrayList<EduWebTest>();
        for (EduWebTest entity : sources) {
            if ("".equals(filter)) {
                targets.add(entity);
            } else {
                if (type == 1) {
                    if (entity.getCompanyNm().contains(filter)) {
                        targets.add(entity);
                    }
                } else if (type == 2) {
                    if (entity.getCeo().contains(filter)) {
                        targets.add(entity);
                    }
                } else if (type == 3) {
                    if (entity.getBizno().equals(filter)) {
                        targets.add(entity);
                    }
                } else if (type == 4) {
                    if (entity.getUseYn().equals(filter)) {
                        targets.add(entity);
                    }
                }
            }
        }
        return targets;
    }


    //QueryDsl
    public List<EduWebTest> getByQeuryDsl(RequestParams<EduWebTest> requestParams) {
        String companyNm = requestParams.getString("companyNm", "");
        String ceo = requestParams.getString("ceo", "");
        String bizno = requestParams.getString("bizno", "");
        String useYn = requestParams.getString("useYn", "");
        logger.info("회사명" + companyNm);
        logger.info("대표자" + ceo);
        logger.info("사업자 번호" + bizno);
        logger.info("사용여부" + useYn);


        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(companyNm)) {
            builder.and(qEduWebTest.companyNm.contains(companyNm));
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
        for (EduWebTest eduWebTest : request) {
            if (eduWebTest.isCreated()) {
                save(eduWebTest);
            } else if (eduWebTest.isModified()) {
                update(qEduWebTest)
                        .set(qEduWebTest.companyNm, eduWebTest.getCompanyNm())
                        .set(qEduWebTest.ceo, eduWebTest.getCeo())
                        .set(qEduWebTest.bizno, eduWebTest.getBizno())
                        .set(qEduWebTest.tel, eduWebTest.getTel())
                        .set(qEduWebTest.email, eduWebTest.getEmail())
                        .set(qEduWebTest.useYn, eduWebTest.getUseYn())
                        .where(qEduWebTest.id.eq(eduWebTest.getId()))
                        .execute();
            } else if (eduWebTest.isDeleted()) {
                delete(qEduWebTest)
                        .where(qEduWebTest.id.eq(eduWebTest.getId()))
                        .execute();
            }
        }

    }


    public EduWebTest getByOne(long id) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qEduWebTest.id.eq(id));
        EduWebTest eduWebTest = select().from(qEduWebTest).where(builder).fetchOne();
        return eduWebTest;
    }

    //QureyDsl 건별로
    public List<EduWebTest> gets(String companyNm, String ceo, String bizno, String useYn) {
        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(companyNm)) {
            builder.and(qEduWebTest.companyNm.like("%" + companyNm + "%"));
        }
        if (isNotEmpty(ceo)) {
            builder.and(qEduWebTest.ceo.like("%" + ceo + "%"));
        }
        if (isNotEmpty(bizno)) {
            builder.and(qEduWebTest.bizno.like("%" + bizno + "%"));
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

    public EduWebTest getByID(Long id) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qEduWebTest.id.eq(id));
        EduWebTest eduWebTest = select()
                .from(qEduWebTest)
                .where(builder)
                .orderBy(qEduWebTest.companyNm.asc())
                .fetchOne();
        return eduWebTest;
    }

    @Transactional
    public void persist(EduWebTest request) {//return 값 없이 void로
        if (request.getId() == null || request.getId() == 0) {
            save(request);//request entity
        } else {//querydsl 방식으로 update
            update(qEduWebTest)
                    .set(qEduWebTest.companyNm, request.getCompanyNm())
                    .set(qEduWebTest.ceo, request.getCeo())
                    .set(qEduWebTest.bizno, request.getBizno())
                    .set(qEduWebTest.tel, request.getTel())
                    .set(qEduWebTest.zip, request.getZip())
                    .set(qEduWebTest.address, request.getAddress())
                    .set(qEduWebTest.addressDetail, request.getAddressDetail())
                    .set(qEduWebTest.email, request.getEmail())
                    .set(qEduWebTest.remark, request.getRemark())
                    .set(qEduWebTest.useYn, request.getUseYn())
                    .where(qEduWebTest.id.eq(request.getId()))
                    .execute();
        }

    }


    @Transactional
    public void remove(Long id) {//return 값 없이 void로
        delete(qEduWebTest)
                .where(qEduWebTest.id.eq(id))
                .execute();
    }


    //MyBatis

    public List<EduWebTest> select(String companyNm, String ceo, String bizno, String useYn) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("companyNm", companyNm);
        params.put("ceo", ceo);
        params.put("bizno", bizno);
        params.put("useYn", useYn);

        List<EduWebTest> list = eduWebTestMapper.select(params);
        return list;
    }


    public EduWebTest selectOne(Long id) {
        return eduWebTestMapper.selectOne(id);
    }

    public void enroll(EduWebTest request) {
        if (request.getId() == null || request.getId() == 0) {
            eduWebTestMapper.insert(request);
        } else {
            eduWebTestMapper.update(request);
        }
    }

    public void del(Long id) {
        eduWebTestMapper.delete(id);
    }

    public Page<EduWebTest> getPages(RequestParams<EduWebTest> requestParams) {
        List<EduWebTest> list = this.getByQeuryDsl(requestParams);
        Pageable pageable = requestParams.getPageable();
        int start = (int) pageable.getOffset();
        int end = (start + pageable.getPageSize() > list.size() ? list.size() : (start + pageable.getPageSize()));
        Page<EduWebTest> pages = new PageImpl<>(list.subList(start, end), pageable, list.size());
        return pages;

    }

//try catch
    public List<EduWebTest> getListUsingMyBatis(RequestParams<EduWebTest> requestParams) {
        String companyNm = requestParams.getString("companyNm", "");
        String ceo = requestParams.getString("ceo", "");
        String bizno = requestParams.getString("bizno", "");
        String useYn = requestParams.getString("useYn", "");

        if (!"".equals(useYn) && !"Y".equals(useYn) && !"N".equals(useYn)) {
            throw new RuntimeException("Y 아니면 N 입력하세요~");
        }

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("companyNm", companyNm);
        params.put("ceo", ceo);
        params.put("bizno", bizno);
        params.put("useYn", useYn);

        List<EduWebTest> list = eduWebTestMapper.errlog(params);
        return list;
    }

}



