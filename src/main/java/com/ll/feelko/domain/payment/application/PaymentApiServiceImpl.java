package com.ll.feelko.domain.payment.application;


import com.ll.feelko.domain.experience.dao.ExperienceRepository;
import com.ll.feelko.domain.experience.entity.Experience;
import com.ll.feelko.domain.payment.dao.PaymentRepository;
import com.ll.feelko.domain.payment.entity.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.ll.feelko.global.common.entity.PaymentStatus.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentApiServiceImpl implements PaymentApiService{

    private final PaymentRepository paymentRepository;

    private final ExperienceRepository experienceRepository;


    @Override
    @Transactional
    public void payment(String userEmail,
                        Long headcount,
                        LocalDate reservationDate,
                        String paymentKey,
                        BigDecimal amount) {

        Payment payment = Payment.builder()
                .email(userEmail)
                .status(COMPLETE_PAYMENT)
                .headCount(headcount)
                .paymentKey(paymentKey)
                .price(amount)
                .reservationDate(reservationDate).build();

        paymentRepository.save(payment);
    }


    @Override
    @Transactional
    public void decreaseParticipants(Long getExperiencesId,
                                     Long headcount

    ) {
        Experience experience =
                experienceRepository
                        .findById(getExperiencesId)
                        .orElseThrow();

        experience.headcountReduction(headcount);

    }



}
