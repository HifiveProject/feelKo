package com.ll.feelko.domain.experience.api;

import com.ll.feelko.domain.experience.application.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping("/answer/create/{experienceId}")
    public String createAnswer(@PathVariable("experienceId") Long experienceId,
                               @RequestParam("content") String content) {
        answerService.createAnswer(experienceId, content);
        return "redirect:/experiences/" + experienceId;
    }
}