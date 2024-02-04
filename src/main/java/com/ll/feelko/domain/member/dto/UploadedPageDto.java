package com.ll.feelko.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class UploadedPageDto {
    private long id;
    private String imageUrl;
    private String title;
    private BigDecimal price;
    private LocalDate startDate;
    private LocalDate endDate;
    //이미지
}
