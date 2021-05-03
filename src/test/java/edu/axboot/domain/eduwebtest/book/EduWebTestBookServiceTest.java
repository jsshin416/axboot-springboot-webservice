package edu.axboot.domain.eduwebtest.book;

import edu.axboot.AXBootApplication;
import edu.axboot.controllers.dto.EduWebTestResponseDto;
import edu.axboot.controllers.dto.EduWebTestSaveRequestDto;
import edu.axboot.controllers.dto.EduWebTestUpdateRequestDto;
import edu.axboot.domain.eduwebtest.EduWebTestService;
import lombok.extern.java.Log;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



import static org.junit.Assert.assertTrue;

@Log
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AXBootApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)//이름 순서대로

public class EduWebTestBookServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(EduWebTestService.class);

    @Autowired
    private  EduWebTestBookService eduWebTestBookService;

    public  static long testId =0;

    @Test
    public  void test1_거래처_한건_저장하기(){
        //given
        EduWebTestSaveRequestDto saveDto = EduWebTestSaveRequestDto.builder()
                .companyNm("단위테스트")
                .ceo("단위")
                .build();

        //when
       testId = this.eduWebTestBookService.save(saveDto);
       logger.info("ID=================>"+ testId);

        //then
        assertTrue(testId >0);
    }

    @Test
    public  void test2_거래처_한건_불러오기(){
        //given
        Long id = testId;
        //when
        EduWebTestResponseDto result = this.eduWebTestBookService.findById(id);
        logger.info("ID==========>"+result.getId());
        //then
        assertTrue(result.getId()==id);
    }

    @Test
    public  void test3_거래처_한건_수정하고_확인하기(){
        //given
        Long id = testId;
        //when
        EduWebTestUpdateRequestDto updateDto = EduWebTestUpdateRequestDto.builder()
                .tel("010-9566-8555")
                .email("ggg@nnn")
                .build();
        Long result = this.eduWebTestBookService.update(id,updateDto);
        EduWebTestResponseDto responseDto = this.eduWebTestBookService.findById(id);
        logger.info("ID==========>"+updateDto.getTel()+responseDto.getTel());
        logger.info("ID==========>"+updateDto.getEmail()+responseDto.getEmail());

        //then
        //assertTrue(testId == result);
        assertTrue(responseDto.getTel().equals(updateDto.getTel())&&responseDto.getEmail().equals(updateDto.getEmail()));
    }

}
