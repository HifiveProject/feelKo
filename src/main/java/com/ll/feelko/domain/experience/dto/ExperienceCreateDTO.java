package com.ll.feelko.domain.experience.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceCreateDTO {
    private Long memberId;
    private String title;
    private List<MultipartFile> imageFiles;
    private String location;
    public String descriptionText;
    private BigDecimal price;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long headcount;
    private Long wishCounter;
    private String answer;
    //private boolean end_priod;
}
