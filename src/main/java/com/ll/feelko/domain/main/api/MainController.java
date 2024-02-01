package com.ll.feelko.domain.main.api;


import com.ll.feelko.domain.experience.application.ExperienceService;
import com.ll.feelko.domain.experience.entity.Experience;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

@Slf4j
@Controller
public class MainController {
    @Autowired
    private ExperienceService experienceService;

    @GetMapping("/")
    public String showMain() {
        log.info("called");
        return "/domain/main/mainpage";
    }

//    @GetMapping("/experienceList")
//    public String showExperienceList(){
//        log.info("called");
//        return "/domain/main/experienceList";
//    }

//    @GetMapping("/search")
//    public String search(@RequestParam(name = "destination", required = false) String destination,
////                         @RequestParam(name = "start_date", required = false) String startDate,
//                         Model model) {
//
//        // 검색어를 이용한 비즈니스 로직 수행 (날짜는 제외)
//        List<Experience> experiences = experienceService.searchExperiences(destination, pageable);
//
//        // 모델에 결과 추가
//        model.addAttribute("experiences", experiences);
//        model.addAttribute("selectedLocation", destination);
//        return "domain/main/experienceList";
//    }


    @GetMapping("/search")
    public String experienceList(
            @RequestParam(name = "destination", required = false) String destination,
            @RequestParam(name = "page", defaultValue = "0") int page,
            Model model
    ) {

        if (StringUtils.isEmpty(destination) || destination.equals("전국")) {
            // 전국이 선택된 경우 또는 destination이 빈 문자열인 경우 전체 지역 검색
            Pageable pageable = PageRequest.of(page, 9);
            Page<Experience> experiencePage = experienceService.searchAllExperiences(pageable);
            model.addAttribute("experiences", experiencePage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", experiencePage.getTotalPages());
            model.addAttribute("selectedLocation", destination);
        } else {


            Pageable pageable = PageRequest.of(page, 9);
            Page<Experience> experiencePage = experienceService.searchExperiences(destination, pageable);

            model.addAttribute("experiences", experiencePage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("selectedLocation", destination);
            model.addAttribute("totalPages", experiencePage.getTotalPages());
        }
        return "domain/main/experienceList";
    }

}
