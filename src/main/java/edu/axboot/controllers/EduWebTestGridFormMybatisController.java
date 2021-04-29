package edu.axboot.controllers;

import com.chequer.axboot.core.api.response.ApiResponse;
import com.chequer.axboot.core.api.response.Responses;
import com.chequer.axboot.core.controllers.BaseController;
import edu.axboot.domain.eduwebtest.EduWebTest;
import edu.axboot.domain.eduwebtest.EduWebTestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@Controller
@RequestMapping(value = "/api/v1/eduwebtest/myBatis")
public class EduWebTestGridFormMybatisController extends BaseController {

    @Inject
    private EduWebTestService eduwebtestService;

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

}


