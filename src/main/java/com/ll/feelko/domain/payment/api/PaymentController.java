package com.ll.feelko.domain.payment.api;


import com.ll.feelko.domain.experience.application.ExperienceService;
import com.ll.feelko.domain.experience.entity.Experience;
import com.ll.feelko.global.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final ExperienceService experienceService;

    @GetMapping("/payment/{experienceId}/{people}/{dateTime}")
    public String test(@PathVariable("experienceId") Long experienceId ,
                       @PathVariable("people") Long count ,
                       @PathVariable("dateTime") LocalDate day,
                       Model model) {

        Experience experiences = experienceService.findByIdElseThrow(experienceId);

        model.addAttribute("experiences" ,  experiences);
        model.addAttribute("count" ,  count);
        model.addAttribute("day" ,  day);

        return "domain/payment/payments";
    }

    //태훈 :
    @GetMapping("/success/{count}/{dateTime}")
    @ResponseBody
    public String handleSuccess(
            @PathVariable(name = "count") Long count,
            @PathVariable("dateTime") LocalDate day,
            @RequestParam(name = "orderId") String orderId,
            @RequestParam(name = "paymentKey") String paymentKey,
            @RequestParam(name = "amount") int amount,
            @AuthenticationPrincipal SecurityUser user,
            Model model
    ) {
        log.info("참자가 인원수 = {}" , count);
        log.info("날짜 = {}" , day);
        log.info("예약자 이메일= {}" , user.getName()); //이름 으로 변경할수있나요?
        log.info("예약 번호 = {}" , paymentKey);
        log.info("체험 금액 = {}" , amount);



        //!!! -
        return "성공";
    }
}
