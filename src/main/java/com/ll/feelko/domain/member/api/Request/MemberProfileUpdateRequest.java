package com.ll.feelko.domain.member.api.Request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class MemberProfileUpdateRequest {
    private String email;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate birthday;
    private String phone;
    private String profile;
}

