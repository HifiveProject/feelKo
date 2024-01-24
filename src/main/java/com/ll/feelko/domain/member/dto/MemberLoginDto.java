package com.ll.feelko.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberLoginDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
