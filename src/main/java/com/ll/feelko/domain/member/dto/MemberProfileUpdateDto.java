package com.ll.feelko.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberProfileUpdateDto {
    private String email;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate birthday;
    private String phone;
    private String profile;
}
