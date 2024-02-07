package com.ll.feelko.domain.experience.form;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ExperienceCreateForm {
    private String title;
    private String location;
    public String descriptionText;
    private BigDecimal price;
    private LocalDate startDate;
    private Long headcount;
    private String answer;
}
