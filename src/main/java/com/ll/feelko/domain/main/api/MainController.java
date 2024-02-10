package com.ll.feelko.domain.main.api;


import com.ll.feelko.domain.experience.application.ExperienceService;
import com.ll.feelko.domain.experience.entity.Experience;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Controller
public class MainController {

    @Autowired
    private ExperienceService experienceService;

    @GetMapping("/search")
    public String experienceList(
            @RequestParam(name = "destination", required = false) String destination,
            @RequestParam(name = "selectDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate selectDate,
            @RequestParam(name = "include_closing", defaultValue = "false") boolean includeClosing,
            @RequestParam(name = "page", defaultValue = "1") int page,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page - 1, 12);
        Page<Experience> experiencePage;

        if (StringUtils.isEmpty(destination) || destination.equals("전국")) {
            // 전국이 선택된 경우 또는 destination이 빈 문자열인 경우 전체 지역 검색
            if (includeClosing) {
                experiencePage = experienceService.searchAllExperiencesIncludingClosing(pageable);
            } else {
                experiencePage = experienceService.searchAllExperiences(pageable);
            }
        } else {
            // 특정 지역이 선택된 경우 해당 지역의 경험 검색
            if (selectDate == null) {
                if(includeClosing)
                {
                    experiencePage = experienceService.searchExperiencesIncludingClosing(destination, selectDate, pageable);
                }
                else {
                    experiencePage = experienceService.searchExperiencesByLocation(destination, pageable);
                }
            } else {
                if (includeClosing) {
                    experiencePage = experienceService.searchExperiencesIncludingClosing(destination, selectDate, pageable);
                } else {
                    experiencePage = experienceService.searchExperiencesByDateRange(destination, selectDate, pageable);
                }
            }
        }

        // 페이지네이션을 위한 상수 (한 페이지네이션에 표시될 페이지 수)
        final int PAGE_BLOCK = 5;

        // 현재 페이지 그룹의 시작 페이지 계산 (1, 6, 11, ...)
        int startBlockPage = ((page - 1) / PAGE_BLOCK) * PAGE_BLOCK + 1;

        // 현재 페이지 그룹의 끝 페이지 계산
        int endBlockPage = Math.min(startBlockPage + PAGE_BLOCK - 1, experiencePage.getTotalPages());

        model.addAttribute("startBlockPage", startBlockPage);
        model.addAttribute("endBlockPage", endBlockPage);
        model.addAttribute("experiences", experiencePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", experiencePage.getTotalPages());
        model.addAttribute("selectedLocation", destination);
        model.addAttribute("selectDate", selectDate);

        return "domain/main/experienceList";
    }

    @GetMapping("/")
    public String popularExperiences(Model model) {
        Pageable pageable = PageRequest.of(0, 8); // 최대 6개의 결과를 가져오도록 설정
        List<Experience> popularExperiences = experienceService.findPopularExperiences(pageable);

        // 마감 임박 체험 리스트 가져오기
        List<Experience> closingSoonExperiences = experienceService.findClosingSoonExperiences();

        model.addAttribute("popularExperiences", popularExperiences);
        model.addAttribute("closingSoonExperiences", closingSoonExperiences);
        return "domain/main/mainpage";
    }
}