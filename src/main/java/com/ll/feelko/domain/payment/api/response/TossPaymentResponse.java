package com.ll.feelko.domain.payment.api.response;

import com.ll.feelko.domain.experience.entity.Experience;
import com.ll.feelko.global.common.entity.PaymentStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class TossPaymentResponse {

    private String email;

    private Experience experience;

    private PaymentStatus status;

    private LocalDate reservationDate;

    private Long headCount;

    private String paymentKey;

    private BigDecimal price;
}
