package edu.axboot.controllers.dto;

import edu.axboot.domain.eduwebtest.book.EduWebTestBook;
import lombok.Getter;

@Getter
public class EduWebTestListResponseDto {
    private String companyNm;
    private String ceo;
    private String useYn;


    public EduWebTestListResponseDto(EduWebTestBook entity) {
        this.companyNm = entity.getCompanyNm();
        this.ceo = entity.getCeo();
        this.useYn = entity.getUseYn();
    }
}
