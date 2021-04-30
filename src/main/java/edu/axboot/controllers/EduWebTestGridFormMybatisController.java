package edu.axboot.controllers;

import antlr.Utils;
import com.chequer.axboot.core.api.BadRequestException;
import com.chequer.axboot.core.api.response.ApiResponse;
import com.chequer.axboot.core.api.response.Responses;
import com.chequer.axboot.core.controllers.BaseController;
import com.chequer.axboot.core.parameter.RequestParams;
import edu.axboot.domain.eduwebtest.EduWebTest;
import edu.axboot.domain.eduwebtest.EduWebTestService;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@Controller
@RequestMapping(value = "/api/v1/eduwebtest/myBatis")
public class EduWebTestGridFormMybatisController extends BaseController {

    @Inject
    private EduWebTestService eduwebtestService;
    private Utils logger;


    @RequestMapping(method = RequestMethod.GET, produces = APPLICATION_JSON)
    public Responses.ListResponse list(
            @RequestParam(value ="companyNm", required = false) String companyNm,
            @RequestParam(value ="ceo", required = false) String ceo,
            @RequestParam(value ="bizno", required = false) String bizno,
            @RequestParam(value ="useYn", required = false) String useYn) {
        List<EduWebTest> list = eduwebtestService.select(companyNm,ceo,bizno,useYn);
        return Responses.ListResponse.of(list);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = APPLICATION_JSON)
    public EduWebTest selectOne(@PathVariable Long id) {
        EduWebTest eduWebTest = eduwebtestService.selectOne(id);
        return eduWebTest;
    }

    @RequestMapping(method = RequestMethod.POST, produces = APPLICATION_JSON)
    public ApiResponse save(@RequestBody EduWebTest request) {
        eduwebtestService.enroll(request);
        return ok();
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = APPLICATION_JSON)
    public ApiResponse delete(@ PathVariable Long id) {
        eduwebtestService.del(id);
        return ok();
    }

    @RequestMapping(value="/mybatis", method = RequestMethod.GET, produces = APPLICATION_JSON)
    public Responses.ListResponse listByMyBatis(@RequestParam(value = "companyNm", required = false) String companyNm,
                                                @RequestParam(value = "ceo", required = false) String ceo,
                                                @RequestParam(value = "bizno", required = false) String bizno,
                                                @RequestParam(value = "useYn", required = false) String useYn) {
        try {
            RequestParams<EduWebTest> requestParams = new RequestParams<>();
            requestParams.put("companyNm", companyNm);
            requestParams.put("ceo", ceo);
            requestParams.put("bizno", bizno);
            requestParams.put("useYn", useYn);
            List<EduWebTest> list = eduwebtestService.getListUsingMyBatis(requestParams);
            return Responses.ListResponse.of(list);
        } catch (BadSqlGrammarException e) {
            logger.error("마이바티스 조회 오류. 쿼리 확인해 보세요~");
            return Responses.ListResponse.of(null);
        }catch (RuntimeException e){
            logger.error(e.getMessage());
            return Responses.ListResponse.of(null);
        }
    }

}


