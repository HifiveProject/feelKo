package com.ll.feelko.domain.main.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MainController {
    @GetMapping("/")
    public String showMain(){
        log.info("called");
        return "/domain/main/experienceList";
    }


}
