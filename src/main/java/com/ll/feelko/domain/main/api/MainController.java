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
import java.util.Arrays;
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
            if (selectDate != null) {
                // "전국" 선택되고 특정 날짜가 선택된 경우, 마감 체크 옵션 고려
                experiencePage = experienceService.searchExperiencesForAllLocationsOnDateWithClosing(selectDate, includeClosing, pageable);
            } else {
                // "전국" 선택되고 날짜가 선택되지 않은 경우, 마감 체크 옵션 고려
                experiencePage = experienceService.searchAllExperiencesIncludingClosing(includeClosing, pageable);
            }
        } else {
            // 특정 지역이 선택된 경우 해당 지역의 경험 검색
            if (selectDate == null) {
                if (includeClosing) {
                    experiencePage = experienceService.searchExperiencesIncludingClosing(destination, selectDate, pageable);
                } else {
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
        int endBlockPage = experiencePage.getTotalPages() > 0 ? Math.min(startBlockPage + PAGE_BLOCK - 1, experiencePage.getTotalPages()) : 1;

        List<String> allDestinations = Arrays.asList("전국", "서울", "인천", "대전", "대구", "경기", "부산", "울산", "광주", "강원", "충북", "충남", "경북", "경남", "전북", "전남", "제주", "세종");

        model.addAttribute("includeClosing", includeClosing);
        model.addAttribute("allDestinations", allDestinations);
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
    public String mainPage(@RequestParam(name = "destination", required = false) String destination,
                                     @RequestParam(name = "selectDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate selectDate,
                                     @RequestParam(name = "include_closing", defaultValue = "false") boolean includeClosing,
                                     Model model) {
        Pageable pageable = PageRequest.of(0, 8); // 최대 6개의 결과를 가져오도록 설정
        List<Experience> popularExperiences = experienceService.findPopularExperiences(pageable);
        // 마감 임박 체험 리스트 가져오기
        List<Experience> closingSoonExperiences = experienceService.findClosingSoonExperiences();

        List<String> allDestinations = Arrays.asList("전국", "서울", "인천", "대전", "대구", "경기", "부산", "울산", "광주", "강원", "충북", "충남", "경북", "경남", "전북", "전남", "제주", "세종");

        model.addAttribute("includeClosing", includeClosing);
        model.addAttribute("allDestinations", allDestinations);
        model.addAttribute("popularExperiences", popularExperiences);
        model.addAttribute("closingSoonExperiences", closingSoonExperiences);
        model.addAttribute("selectedLocation", destination);
        model.addAttribute("selectDate", selectDate);
        return "domain/main/mainpage";
    }
}