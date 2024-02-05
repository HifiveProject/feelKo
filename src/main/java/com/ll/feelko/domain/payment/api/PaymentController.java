package com.ll.feelko.domain.payment.api;


import com.ll.feelko.domain.experience.application.ExperienceService;
import com.ll.feelko.domain.experience.entity.Experience;
import com.ll.feelko.domain.payment.api.response.TossPaymentResponse;
import com.ll.feelko.domain.payment.application.PaymentApiService;
import com.ll.feelko.domain.payment.dao.PaymentDetailsRepository;
import com.ll.feelko.global.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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

    //태훈 :
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

        return "redirect:/reservation-details/" + user.getId();
    }

    @GetMapping({"/reservation-details/{memberId}"})
    @ResponseBody
    public String reservation(@PathVariable(name = "memberId") Long memberId , Model model) {

        List<TossPaymentResponse> reservations =
                paymentDetailsRepository.getAllReservations(memberId);

        model.addAttribute("reservations", reservations);

        for (TossPaymentResponse reservation : reservations) {
            log.info("모든 예약 정보 = {}" , reservation);
        }

        log.info("memberId = {}" ,memberId);

        return "/domain/member/mypage/reservation-details-page";
    }
}