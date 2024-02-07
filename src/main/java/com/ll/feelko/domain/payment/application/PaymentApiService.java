package com.ll.feelko.domain.payment.application;


import com.ll.feelko.domain.payment.entity.Payment;

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
}
