package com.ll.feelko.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @Email(regexp = ".+@.+\\..+", message = "유효한 이메일 형식을 입력해주세요.")
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    private String profile;

    @NotBlank
    @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = "유효한 전화번호 형식을 입력해주세요.")
    private String phone;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private String providerId;

    private String status;
}
