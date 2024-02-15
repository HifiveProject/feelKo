package com.ll.feelko.domain.member.dto;

import com.ll.feelko.domain.experience.entity.ExperienceImage;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class UploadedPageDto {
    private long id;
    private String title;
    private BigDecimal price;
    private LocalDate startDate;
    private LocalDate endDate;
    //이미지
    private String imageUrl;

    public UploadedPageDto(long id, String title, ExperienceImage images, BigDecimal price, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.imageUrl = images.getImage().get(0);

    }

}
