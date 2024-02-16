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
import java.nio.file.Paths;
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
        String basePath = System.getProperty("user.dir");
        return Paths.get(basePath, fileDir, filename).toString();
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
                .originalHeadcount(dto.getHeadcount())
                .experienceClose(dto.getExperienceClose())
                .answer(dto.getAnswer())
                .build();

        return experienceRepository.save(experience);

    }

    public void upload(ExperienceCreateForm multipartFile, Experience experience) {
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
                .limit(4)
                .collect(Collectors.toList());

        return closingSoonExperiences;
    }

    public Page<Experience> searchAllExperiencesIncludingClosing(boolean includeClosing, Pageable pageable) {
        if (includeClosing) {
            return experienceRepository.searchAllExperiencesIncludingClosing(pageable);
        } else {
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

    public Page<Experience> searchExperiencesForAllLocationsOnDateWithClosing(LocalDate selectDate, boolean includeClosing, Pageable pageable) {
        return experienceRepository.findByAllLocationsOnDateWithClosing(selectDate, includeClosing, pageable);
    }
}
