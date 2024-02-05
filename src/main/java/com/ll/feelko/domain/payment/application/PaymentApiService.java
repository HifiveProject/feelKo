package com.ll.feelko.domain.payment.application;


import com.ll.feelko.domain.payment.dto.PaymentDetailDto;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface PaymentApiService {
    void payment(String userEmail,
                        Long headcount,
                        LocalDate reservationDate,
                        String paymentKey,
                        BigDecimal amount);

    void decreaseParticipants(Long getExperiencesId,
                         Long headcount);

    PaymentDetailDto findPaymentDetailByPaymentId(Long paymentId);

    boolean isMyPayment(Long memberId, Long paymentId);
}
