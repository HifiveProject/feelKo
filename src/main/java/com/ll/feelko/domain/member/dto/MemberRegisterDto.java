package com.ll.feelko.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
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
public class MemberRegisterDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;

    private String name;

    private String profile;
    private String phone;
    @DateTimeFormat
    private LocalDate birthday;

}
