package edu.axboot.domain.eduwebtest.book;

import com.chequer.axboot.core.api.response.Responses;
import com.chequer.axboot.core.parameter.RequestParams;
import com.querydsl.core.BooleanBuilder;
import edu.axboot.controllers.dto.EduWebTestListResponseDto;
import edu.axboot.controllers.dto.EduWebTestSaveRequestDto;
import edu.axboot.controllers.dto.EduWebTestResponseDto;
import edu.axboot.controllers.dto.EduWebTestUpdateRequestDto;
import edu.axboot.domain.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EduWebTestBookService extends BaseService<EduWebTestBook, Long> {
    private final EduWebTestBookRepository eduWebTestBookRepository;

    @Transactional
    public Long save(EduWebTestSaveRequestDto requestDto) {
        return eduWebTestBookRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, EduWebTestUpdateRequestDto requestDto) {
        EduWebTestBook eduWebTestBook = eduWebTestBookRepository.findOne(id);

        if (eduWebTestBook == null) {
            throw new IllegalArgumentException("해당 거래처가 없습니다. id=" + id);
        }
        eduWebTestBook.update(requestDto.getTel(), requestDto.getEmail());
        return id;
    }


    public EduWebTestResponseDto findById(Long id) {
        EduWebTestBook entity = eduWebTestBookRepository.findOne(id);
        if (entity == null) {
            throw new IllegalArgumentException("해당 거래처가 없습니다. id=" + id);
        }
        return new EduWebTestResponseDto(entity);
    }


    @Transactional(readOnly = true)
    public List<EduWebTestListResponseDto> findByL(String companyNm, String ceo, String bizno, String useYn) {
        BooleanBuilder builder = new BooleanBuilder();
        if(isNotEmpty(companyNm)){
            builder.and(qEduWebTestBook.companyNm.like("%" + companyNm +"%"));
        }
        if(isNotEmpty(ceo)){
            builder.and(qEduWebTestBook.ceo.like("%" + ceo +"%"));
        }
        if(isNotEmpty(bizno)){
            builder.and(qEduWebTestBook.bizno.like("%" + bizno +"%"));
        }
        if(isNotEmpty(useYn)){
            builder.and(qEduWebTestBook.useYn.like("%" + useYn +"%"));
        }
        List<EduWebTestBook> entitis = select()
                .from(qEduWebTestBook)
                .where(builder)
                .orderBy(qEduWebTestBook.companyNm.asc())
                .fetch();

        return entitis.stream()
            .map(EduWebTestListResponseDto::new)//
            .collect(Collectors.toList());
    }
}


