package com.ll.feelko.domain.wishlist.dto;

import com.ll.feelko.domain.experience.entity.ExperienceImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class WishListPageDto {
    private long id;
    private String imageUrl;
    private String title;
    private BigDecimal price;
    private LocalDate startDate;
    private LocalDate endDate;

    public WishListPageDto(long id, ExperienceImage images, String title, BigDecimal price, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.imageUrl = images.getImage().get(0);
        this.title = title;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
