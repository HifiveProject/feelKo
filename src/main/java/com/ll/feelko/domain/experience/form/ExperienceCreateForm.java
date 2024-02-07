package com.ll.feelko.domain.experience.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ExperienceCreateForm {
    private String title;
    private String location;
    public String descriptionText;
    private BigDecimal price;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private Long headcount;
    private String answer;

    private Boolean experienceClose = false;

}
