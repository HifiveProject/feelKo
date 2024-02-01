package com.ll.feelko.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class uploadReservationDto {
    private String name;
    private String phoneNumber;
    private String email;
    @DateTimeFormat
    private LocalDate reservationDate;
}
