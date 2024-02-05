package com.ll.feelko.domain.payment.api;


import com.ll.feelko.domain.experience.application.ExperienceService;
import com.ll.feelko.domain.experience.entity.Experience;
import com.ll.feelko.domain.payment.application.PaymentApiService;
import com.ll.feelko.domain.payment.dao.PaymentDetailsRepository;
import com.ll.feelko.domain.payment.dto.PaymentDetailDto;
import com.ll.feelko.global.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final ExperienceService experienceService;

    private final PaymentApiService paymentApiService;

    private final PaymentDetailsRepository paymentDetailsRepository;

    @GetMapping("/payment/{experienceId}/{headcount}/{reservationDate}")
    public String test(@PathVariable("experienceId") Long experienceId ,
                       @PathVariable("headcount") Long headcount ,
                       @PathVariable("reservationDate") LocalDate reservationDate,
                       Model model) {

        Experience experiences = experienceService.findByIdElseThrow(experienceId);

        BigDecimal result = experienceService.sumPrice(experiences.getPrice(), headcount);


        model.addAttribute("experiences" ,  experiences);
        model.addAttribute("count" ,  headcount);
        model.addAttribute("day" ,  reservationDate);
        model.addAttribute("hap" ,  result);


        return "domain/payment/payments";
    }

    @GetMapping("/success/{headcount}/{reservationDate}/{experiencesId}")
    public String handleSuccess(
            @PathVariable(name = "headcount") Long headcount,
            @PathVariable("reservationDate") LocalDate date,
            @PathVariable("experiencesId") Long experiencesId,
            @RequestParam(name = "orderId") String orderId,
            @RequestParam(name = "paymentKey") String paymentKey,
            @RequestParam(name = "amount") BigDecimal amount,
            @AuthenticationPrincipal SecurityUser user,
            Model model
    ) {
        log.info("참자가 인원수 = {}" , headcount);
        log.info("날짜 = {}" , date);
        log.info("체험 정보 페이지 = {}" , experiencesId);
        log.info("예약자 이메일= {}" , user.getName());
        log.info("예약 번호 = {}" , paymentKey);
        log.info("체험 금액 = {}" , amount);

        paymentApiService.payment(user.getName() , headcount , date , paymentKey , amount);

        paymentApiService.decreaseParticipants(experiencesId , headcount);

        return "redirect:/member/mypage/reservation-list";
    }

    @GetMapping("/payment/detail/{paymentId}")
    @ResponseBody
    public ResponseEntity<?> showPaymentDetail(@AuthenticationPrincipal SecurityUser user,
                                               @PathVariable Long paymentId){
        if(!paymentApiService.isMyPayment(user.getId(),paymentId)){
            return ResponseEntity.badRequest().body("페이지에 접근할 권한이 없습니다.");
        }

        PaymentDetailDto detail = paymentApiService.findPaymentDetailByPaymentId(paymentId);

        return ResponseEntity.ok(detail);
    }
}