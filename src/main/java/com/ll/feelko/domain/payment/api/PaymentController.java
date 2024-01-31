package com.ll.feelko.domain.payment.api;


import com.ll.feelko.domain.experience.application.ExperienceService;
import com.ll.feelko.domain.experience.entity.Experience;
import com.ll.feelko.global.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final ExperienceService experienceService;

    @GetMapping("/payment/{experienceId}")
    public String test(@PathVariable("experienceId") Long experienceId , Model model) {

        Experience experiences = experienceService.findByIdElseThrow(experienceId);

        model.addAttribute("experiences" ,  experiences);

        return "domain/payment/payments";
    }

    @GetMapping("/success")
    @ResponseBody
    public String handleSuccess(
            @RequestParam(name = "customerName", required = false) String customerName,
            @RequestParam(name = "customerEmail", required = false) String customerEmail,
            @RequestParam(name = "orderId") String orderId,
            @RequestParam(name = "paymentKey") String paymentKey,
            @RequestParam(name = "amount") int amount,
            @AuthenticationPrincipal SecurityUser user,
            Model model
    ) {
        log.info("이름 = {}" , user.getName());
        log.info("order 아이디 = {}" , orderId);
        log.info("key 아이디 = {}" , paymentKey);
        log.info("가격 = {}" , amount);
        
        return "성공";
    }
}
