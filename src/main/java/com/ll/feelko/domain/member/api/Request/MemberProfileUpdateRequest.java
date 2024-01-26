package com.ll.feelko.domain.member.api.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class MemberProfileUpdateRequest {
    @NotBlank
    private String password;

    private String name;

    private String profile;
    private String phone;
    @DateTimeFormat
    private LocalDate birthday;

}
