package com.ll.feelko.domain.wishlist.dto;

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
}
