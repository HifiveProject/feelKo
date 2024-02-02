package com.ll.feelko.domain.member.application;

import com.ll.feelko.domain.experience.dao.ExperienceRepository;
import com.ll.feelko.domain.member.dto.UploadedPageDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MypageServiceImplTest {

    @InjectMocks
    private MypageServiceImpl mypageService;  // 구체적인 구현체 사용

    @Mock
    private ExperienceRepository experienceRepository;

    @Test
    void testGetUploadedPageList() {
        long memberId = 1L;
        // 테스트 데이터 생성
        List<UploadedPageDto> testExperienceList = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            testExperienceList.add(new UploadedPageDto(i,"test"));
        }
        Page<UploadedPageDto> expectedPage = new PageImpl<>(testExperienceList.subList(1, 11),
                PageRequest.of(1, 11),
                testExperienceList.size());

        when(experienceRepository.findIdTitleByMemberIdOrderByIdDesc(memberId, PageRequest.of(1, 10))).thenReturn(expectedPage);

        Page<UploadedPageDto> result = mypageService.getUploadedPageList(memberId, 1, 10);

        assertEquals(expectedPage, result);
    }
}

