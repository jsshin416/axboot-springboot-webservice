package edu.axboot.controllers;

import com.chequer.axboot.core.api.response.Responses;
import com.chequer.axboot.core.controllers.BaseController;
import com.chequer.axboot.core.parameter.RequestParams;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import org.springframework.stereotype.Controller;
import com.chequer.axboot.core.api.response.ApiResponse;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import edu.axboot.domain.eduwebtest.EduWebTest;
import edu.axboot.domain.eduwebtest.EduWebTestService;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import java.util.List;

@Controller
@RequestMapping(value = "/api/v1/eduwebtest")
public class EduWebTestController extends BaseController {

    @Inject
    private EduWebTestService eduwebtestService;

    @RequestMapping(method = RequestMethod.GET, produces = APPLICATION_JSON)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "company", value = "회사명", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "ceo", value = "대표자", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "bizno", value = "사업자 번호", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "useYn", value = "사용유무", dataType = "String", paramType = "query")
    })

    public Responses.ListResponse list(RequestParams<EduWebTest> requestParams) {
        List<EduWebTest> list = eduwebtestService.gets(requestParams);//gets 오버로드
        return Responses.ListResponse.of(list);
    }

    @RequestMapping(method = {RequestMethod.PUT}, produces = APPLICATION_JSON)
    public ApiResponse save(@RequestBody List<EduWebTest> request) {
        eduwebtestService.save(request);
        return ok();
    }


    @RequestMapping(value= "/queryDsl",  method = RequestMethod.GET, produces = APPLICATION_JSON)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "company", value = "회사명", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "ceo", value = "대표자", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "bizno", value = "사업자 번호", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "useYn", value = "사용유무", dataType = "String", paramType = "query")
    })
    public Responses.ListResponse list2(RequestParams<EduWebTest> requestParams) {
        List<EduWebTest> list =this.eduwebtestService.getByQeuryDsl(requestParams);
        return Responses.ListResponse.of(list);

    }


    @RequestMapping(value= "/queryDsl/pages",  method = RequestMethod.GET, produces = APPLICATION_JSON)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "페이지번호(0부터시작)", required = true, dataType = "integer", paramType = "query", defaultValue = "0"),
            @ApiImplicitParam(name = "pageSize", value = "페이지크기",required = true, dataType = "integer", paramType = "query",defaultValue = "50"),
            @ApiImplicitParam(name = "company", value = "회사명", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "ceo", value = "대표자", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "bizno", value = "사업자 번호", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "useYn", value = "사용유무", dataType = "String", paramType = "query")
    })
    public Responses.ListResponse pages(RequestParams<EduWebTest> requestParams) {
        List<EduWebTest> pages =this.eduwebtestService.getPages(RequestParams<EduWebTest> requestParams);
        return Responses.ListResponse.of(pages);

    }



    @RequestMapping(value= "/queryDsl", method = {RequestMethod.PUT}, produces = APPLICATION_JSON)
    public ApiResponse save2(@RequestBody List<EduWebTest> request) {
        eduwebtestService.saveByQuery(request);
        return ok();
    }




    @RequestMapping(value= "/queryDsl/getByOne",  method = RequestMethod.GET, produces = APPLICATION_JSON)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "Long", paramType = "query")
    })
    public EduWebTest One(@RequestParam long id) {
        EduWebTest eduWebTest =this.eduwebtestService.getByOne(id);
        return eduWebTest;

    }



}