package com.ll.feelko.domain.main.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String showMain(){
        return "/domain/main/main";
    }


}
