package edu.axboot.controllers;

import com.chequer.axboot.core.api.response.ApiResponse;
import com.chequer.axboot.core.api.response.Responses;
import com.chequer.axboot.core.controllers.BaseController;
import com.chequer.axboot.core.parameter.RequestParams;
import edu.axboot.controllers.dto.*;
import edu.axboot.domain.eduwebtest.EduWebTest;
import edu.axboot.domain.eduwebtest.book.EduWebTestBook;
import edu.axboot.domain.eduwebtest.book.EduWebTestBookService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Controller
public class EduWebTestBookController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(EduWebTestBookController.class);

    private final EduWebTestBookService eduWebTestBookService;

    @PostMapping("/api/v1/eduwebtest/eduwebtestbook")
    public ApiResponse save(@RequestBody EduWebTestSaveRequestDto requestDto) {
        eduWebTestBookService.save(requestDto);
        return ok();
    }

    @PutMapping("/api/v1/eduwebtest/eduwebtestbook/{id}")
    public ApiResponse update(@PathVariable Long id, @RequestBody EduWebTestUpdateRequestDto requestDto) {
        eduWebTestBookService.update(id, requestDto);
        return ok();
    }

    @GetMapping("/api/v1/eduwebtest/eduwebtestbook/{id}")
    public EduWebTestResponseDto findById(@PathVariable Long id) {
        return eduWebTestBookService.findById(id);
    }

    @GetMapping("/api/v1/eduwebtest/eduwebtestbook")
    public Responses.ListResponse list(
            @RequestParam(value = "companyNm", required = false) String companyNm,
            @RequestParam(value = "ceo", required = false) String ceo,
            @RequestParam(value = "bizno", required = false) String bizno,
            @RequestParam(value = "useYn", required = false) String useYn) {
        List<EduWebTestListResponseDto> list = eduWebTestBookService.findByL(companyNm, ceo, bizno, useYn);
        {
            return Responses.ListResponse.of(list);
        }


    }
}