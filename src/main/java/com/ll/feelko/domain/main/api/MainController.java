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

//    @GetMapping("/")
//    public String showMain(){
//        log.info("called");
//        return "/domain/main/mainpage";
//    }

    @GetMapping("/search")
    public String experienceList(
            @RequestParam(name = "destination", required = false) String destination,
            @RequestParam(name = "start_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "page", defaultValue = "0") int page,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, 9);
        Page<Experience> experiencePage;

        if (StringUtils.isEmpty(destination) || destination.equals("전국")) {
            // 전국이 선택된 경우 또는 destination이 빈 문자열인 경우 전체 지역 검색
            experiencePage = experienceService.searchAllExperiences(pageable);
        } else {
            // 특정 지역이 선택된 경우 해당 지역의 경험 검색
            experiencePage = experienceService.searchExperiences(destination, startDate, pageable);
        }

        model.addAttribute("experiences", experiencePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", experiencePage.getTotalPages());
        model.addAttribute("selectedLocation", destination);

        return "domain/main/experienceList";
    }

    @GetMapping("/")
    public String popularExperiences(Model model) {
        Pageable pageable = PageRequest.of(0, 6); // 최대 6개의 결과를 가져오도록 설정
        List<Experience> popularExperiences = experienceService.findPopularExperiences(pageable);

        model.addAttribute("popularExperiences", popularExperiences);
        return "domain/main/mainpage";
    }
}