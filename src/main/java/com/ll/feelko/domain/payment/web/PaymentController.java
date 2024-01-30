package com.ll.feelko.domain.payment.web;


import com.ll.feelko.domain.experience.application.ExperienceService;
import com.ll.feelko.domain.experience.entity.Experience;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final ExperienceService experienceService;

    @GetMapping("/payment")
    public String test(@RequestParam("experienceId") Long experienceId , Model model) {

        Experience experiences = experienceService.findByIdElseThrow(experienceId);

        model.addAttribute("experiences" ,  experiences);

        return "domain/payment/payments";
    }


    // 결제 한 사람 == 로그인 한 사람
    @GetMapping("/success")
    @ResponseBody
    public String handleSuccess(
            @RequestParam(name = "customerName", required = false) String customerName,
            @RequestParam(name = "customerEmail", required = false) String customerEmail,
            @RequestParam(name = "orderId") String orderId,
            @RequestParam(name = "paymentKey") String paymentKey,
            @RequestParam(name = "amount") int amount
            ) {

        log.info("이름 = {}" , customerName);
        log.info("이메일 = {}" , customerEmail);
        log.info("order 아이디 = {}" , orderId);
        log.info("key 아이디 = {}" , paymentKey);
        log.info("가격 = {}" , amount);

        return "성공";
    }
}
