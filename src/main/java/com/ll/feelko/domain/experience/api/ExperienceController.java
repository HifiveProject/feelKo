package com.ll.feelko.domain.experience.api;

import com.ll.feelko.domain.experience.application.ExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/experiences")
public class ExperienceController {

    private final ExperienceService experienceService;

    @GetMapping("/{experienceId}")
    public String detail(@PathVariable Long experienceId, Model model) {

        model.addAttribute("experience", experienceService.detail(experienceId));

        return "domain/experience/detail";
    }

}
