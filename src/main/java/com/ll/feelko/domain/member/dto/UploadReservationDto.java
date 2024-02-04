package com.ll.feelko.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class UploadReservationDto {
    private String name;
    private String phoneNumber;
    private String email;
    private String profile;
    private LocalDate reservationDate;
}
