package com.ll.feelko.domain.experience.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class ExperienceCreateDTO {
    private Long memberId;
    private String title;
    private MultipartFile imageFile;
    private String location;
}
