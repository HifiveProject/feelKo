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

    private Long paymentId;

    private String paymentKey;

    private BigDecimal price;

    private LocalDate reservationDate;

    //private String status;
}

