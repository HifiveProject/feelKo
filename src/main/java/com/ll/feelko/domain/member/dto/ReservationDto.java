package com.ll.feelko.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ReservationDto {
    private String imageUrl;

    private String title;

    private Long experienceId;

    private String orderId;

    private String paymentKey;
    // 결제 상세 버튼 누르면 findByPaymentKey로 찾기

    private BigDecimal price;

    private LocalDate reservationDate;

    //private String status;
}

