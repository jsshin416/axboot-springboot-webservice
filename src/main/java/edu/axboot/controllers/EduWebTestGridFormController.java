package edu.axboot.controllers;

import com.chequer.axboot.core.api.response.ApiResponse;
import com.chequer.axboot.core.api.response.Responses;
import com.chequer.axboot.core.controllers.BaseController;
import com.chequer.axboot.core.parameter.RequestParams;
import com.chequer.axboot.core.utils.DateUtils;
import com.chequer.axboot.core.utils.ExcelUtils;
import com.wordnik.swagger.annotations.ApiOperation;
import edu.axboot.domain.eduwebtest.EduWebTest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import edu.axboot.domain.eduwebtest.EduWebTestService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/api/v1/edu/teachGridForm")
public class EduWebTestGridFormController extends BaseController {

    @Inject
    private EduWebTestService eduwebtestService;


    @RequestMapping(method = RequestMethod.GET, produces = APPLICATION_JSON)
    public Responses.ListResponse list(//개별 request 파라미터로 받겠다.
                                       @RequestParam(value = "companyNm", required = false) String companyNm,
                                       @RequestParam(value = "ceo", required = false) String ceo,
                                       @RequestParam(value = "bizno", required = false) String bizno,
                                       @RequestParam(value = "useYn", required = false) String useYn) {
        List<EduWebTest> list = eduwebtestService.gets(companyNm, ceo, bizno, useYn);
        return Responses.ListResponse.of(list);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = APPLICATION_JSON)
    public EduWebTest getOne(@PathVariable Long id) {
        EduWebTest entity = this.eduwebtestService.getByID(id);
        return entity;

    }


    @RequestMapping(method = {RequestMethod.POST}, produces = APPLICATION_JSON)
    public ApiResponse save(@RequestBody EduWebTest request) {
        eduwebtestService.persist(request);
        return ok();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = APPLICATION_JSON)
    public ApiResponse remove(@PathVariable Long id) {
        eduwebtestService.remove(id);
        return ok();
    }


    @ApiOperation(value = "엑셀다운로드", notes = "/resources/excel/education_teach.xlsx")
    @RequestMapping(value = "/excelDown", method = {RequestMethod.POST}, produces = APPLICATION_JSON)
    public void excelDown(RequestParams<EduWebTest> requestParams, HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<EduWebTest> list = eduwebtestService.getByQeuryDsl(requestParams);
        ExcelUtils.renderExcel("/excel/education_teach.xlsx", list, "Education_" + DateUtils.getYyyyMMddHHmmssWithDate(), request, response);
    }
}