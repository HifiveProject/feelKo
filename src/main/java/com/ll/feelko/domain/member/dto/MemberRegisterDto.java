package com.ll.feelko.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class MemberRegisterDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;

    private String name;

    private String profile;
    private String phone;
    private Date birthday;
    private String roles;

}
