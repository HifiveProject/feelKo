package com.ll.feelko.domain.member.dto;

import com.ll.feelko.domain.experience.entity.ExperienceImage;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ReservationDto {

    private String title;

    private Long experienceId;

    private Long paymentId;

    private String paymentKey;

    private BigDecimal price;

    private LocalDate reservationDate;

    private String imageUrl;

    //private String status;
    public ReservationDto(String title, Long experienceId, Long paymentId, String paymentKey, BigDecimal price, LocalDate reservationDate, ExperienceImage images) {
        this.title = title;
        this.experienceId = experienceId;
        this.paymentId = paymentId;
        this.paymentKey = paymentKey;
        this.price = price;
        this.reservationDate = reservationDate;
        this.imageUrl = images.getImage().get(0);
    }
}

