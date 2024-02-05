package com.ll.feelko.domain.payment.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PaymentDetailDto {
    private String name;
    private String title;
    private String email;
    private LocalDate reservationDate;
    private long headCount;
    private String paymentKey;
    private BigDecimal price;
}
