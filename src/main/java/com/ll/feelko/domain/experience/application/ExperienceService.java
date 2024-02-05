package com.ll.feelko.domain.experience.application;

import com.ll.feelko.domain.experience.dao.ExperienceRepository;
import com.ll.feelko.domain.experience.dto.ExperienceCreateDTO;
import com.ll.feelko.domain.experience.entity.Experience;
import com.ll.feelko.domain.member.application.MemberService;
import com.ll.feelko.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExperienceService {

    private final MemberService memberService;
    private final ExperienceRepository experienceRepository;

    public Experience createExperience(ExperienceCreateDTO dto) {

        Member member = memberService.findByIdElseThrow(dto.getMemberId());

        //리팩토링
        Experience experience = Experience.builder()
                .memberId(member.getId())
                .title(dto.getTitle())
                .location(dto.getLocation())
                // TODO 추후 삭제 요망
                .headcount(10L)
                // TODO 실제 이미지처리가 필요함
                .imageUrl("https://www.localnaeil.com/FileData/Article/201705/4e808dfd-795e-4671-b596-7e64e22d868a.jpg")
                .descriptionText(dto.getDescriptionText())
                .price(dto.getPrice())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .headcount(dto.getHeadcount())
                .build();
        return experienceRepository.save(experience);
    }

    public BigDecimal sumPrice(BigDecimal price , Long people) {

        BigDecimal result = price;

        return result.multiply(BigDecimal.valueOf(people));

    }

    public Experience detail(Long experienceId) {
        Experience experience = findByIdElseThrow(experienceId);

        return experience;
    }

    public Experience findByIdElseThrow(Long experienceId) {
        return experienceRepository.findById(experienceId)
                .orElseThrow(() -> new RuntimeException("체험을 찾을 수 없습니다."));
    }

    public Page<Experience> searchExperiences(String destination, LocalDate startDate, Pageable pageable) {
        return experienceRepository.searchExperiences(destination, startDate, pageable);
    }

    public Page<Experience> searchAllExperiences(Pageable pageable) {
        return experienceRepository.searchExperiencesAll(pageable);
    }

    public List<Experience> findPopularExperiences(Pageable pageable) {
        return experienceRepository.findPopularExperiences(pageable);
    }
}
