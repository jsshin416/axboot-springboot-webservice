package edu.axboot.controllers.dto;

import edu.axboot.domain.eduwebtest.book.EduWebTestBook;
import lombok.Getter;

@Getter
public class EduWebTestResponseDto {
    private Long id;
    private String companyNm;
    private String ceo;
    private String bizno;
    private String tel;
    private String zip;
    private String address;
    private String addressDetail;
    private String email;
    private String remark;
    private String useYn;

    public EduWebTestResponseDto(EduWebTestBook entity) {
        this.id = entity.getId();
        this.companyNm = entity.getCompanyNm();
        this.ceo = entity.getCeo();
        this.bizno = entity.getBizno();
        this.tel = entity.getTel();
        this.zip = entity.getZip();
        this.address = entity.getAddress();
        this.addressDetail = entity.getAddressDetail();
        this.email = entity.getEmail();
        this.remark = entity.getRemark();
        this.useYn = entity.getUseYn();
    }
}
