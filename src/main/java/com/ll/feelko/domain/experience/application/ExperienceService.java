package com.ll.feelko.domain.experience.application;

import com.ll.feelko.domain.experience.dao.ExperienceRepository;
import com.ll.feelko.domain.experience.dao.JpaImageRepository;
import com.ll.feelko.domain.experience.dto.ExperienceCreateDTO;
import com.ll.feelko.domain.experience.entity.Experience;
import com.ll.feelko.domain.experience.entity.ExperienceImage;
import com.ll.feelko.domain.experience.form.ExperienceCreateForm;
import com.ll.feelko.domain.member.application.MemberService;
import com.ll.feelko.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExperienceService {

    @Value("${image.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    private final MemberService memberService;
    private final JpaImageRepository imageRepository;
    private final ExperienceRepository experienceRepository;

    public Experience createExperience(ExperienceCreateDTO dto) {

        Member member = memberService.findByIdElseThrow(dto.getMemberId());

        if (dto.getStartDate().isBefore(LocalDate.now()) || dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new IllegalArgumentException("시작 날짜는 오늘 이전 날짜를 선택할 수 없으며, 마감 날짜는 시작 날짜 이전 날짜를 선택하라 수 없습니다.");
        }

        //리팩토링
        Experience experience = Experience.builder()
                .memberId(member.getId())
                .title(dto.getTitle())
                .location(dto.getLocation())
                // TODO 추후 삭제 요망
                .headcount(10L)
                .descriptionText(dto.getDescriptionText())
                .price(dto.getPrice())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .headcount(dto.getHeadcount())
                .originalHeadcount(dto.getHeadcount()) // 초기 마감 인원수 설정
                .experienceClose(dto.getExperienceClose())
                .answer(dto.getAnswer())
                .build();

        return experienceRepository.save(experience);

    }

    public void upload(ExperienceCreateForm multipartFile, Experience experience) {
        // 메소드 내부에 지역 변수로 선언하여 각 호출마다 새로운 리스트를 사용하도록 변경
        List<String> files = new ArrayList<>();

        multipartFile.getFile().forEach(f -> {
            try {
                String originalFilename = f.getOriginalFilename();
                final int index = Objects.requireNonNull(originalFilename).lastIndexOf(".");
                final String substring = originalFilename.substring(index + 1);
                final String result = UUID.randomUUID() + "." + substring;
                f.transferTo(new File(getFullPath(result)));
                files.add(result);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        ExperienceImage imageBuilder = ExperienceImage.builder()
                .image(files)
                .experience(experience)
                .build();
        imageRepository.save(imageBuilder);
    }

    public BigDecimal sumPrice(BigDecimal price, Long people) {

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

    public Page<Experience> searchAllExperiences(Pageable pageable) {
        return experienceRepository.searchExperiencesAll(pageable);
    }

    public List<Experience> findPopularExperiences(Pageable pageable) {
        return experienceRepository.findPopularExperiences(pageable);
    }


    public List<Experience> findClosingSoonExperiences() {
        List<Experience> closingSoonExperiences = experienceRepository.findByExperienceCloseFalse().stream()
                .filter(experience -> experience.getHeadcount() != null && experience.isClosingSoon())
                .sorted(Comparator.comparingLong(Experience::getHeadcount))
                .limit(4) // 최대 3개까지만 리스트에 추가
                .collect(Collectors.toList());

        return closingSoonExperiences;
    }

    public Page<Experience> searchAllExperiencesIncludingClosing(boolean includeClosing, Pageable pageable) {
        if (includeClosing) {
            // 마감 임박한 체험을 포함하여 모든 체험 검색
            return experienceRepository.searchAllExperiencesIncludingClosing(pageable);
        } else {
            // 마감 임박한 체험을 제외하고 검색
            // 이 메소드는 마감되지 않은 체험만 검색하는 쿼리를 구현해야 합니다.
            return experienceRepository.findByExperienceCloseFalse(pageable);
        }
    }

    public Page<Experience> searchExperiencesIncludingClosing(String destination, LocalDate selectDate, Pageable pageable) {
        return experienceRepository.searchExperiencesIncludingClosing(destination, selectDate, pageable);
    }

    public Page<Experience> searchExperiencesByDateRange(String destination, LocalDate selectDate, Pageable pageable) {
        return experienceRepository.findByDateRangeAndLocation(destination, selectDate, pageable);
    }

    public Page<Experience> searchExperiencesByLocation(String destination, Pageable pageable) {
        return experienceRepository.findByLocation(destination, pageable);
    }

    //    public Page<Experience> searchExperiences(String destination, LocalDate startDate, Pageable pageable) {
//        return experienceRepository.searchExperiences(destination, startDate, pageable);
//    }

}
