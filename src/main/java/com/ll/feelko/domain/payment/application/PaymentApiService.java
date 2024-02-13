package com.ll.feelko.domain.payment.application;


import com.ll.feelko.domain.experience.entity.Experience;
import com.ll.feelko.domain.member.entity.Member;
import com.ll.feelko.domain.payment.dto.PaymentDetailDto;
import com.ll.feelko.global.security.SecurityUser;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface PaymentApiService {
    void payment( SecurityUser member,
                  Long experience,
                  Long headcount,
                  LocalDate reservationDate,
                  String paymentKey,
                  BigDecimal amount);

    Experience decreaseParticipants(Long getExperiencesId,
                         Long headcount);

    PaymentDetailDto findPaymentDetailByPaymentId(Long paymentId);

    boolean isMyPayment(Long memberId, Long paymentId);


}
