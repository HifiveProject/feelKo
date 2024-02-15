package com.ll.feelko.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SocialLoginDto {
    private String providerTypeCode;
    private String email;
    private String profileImageUrl;
    private String providerId;
    private String nickname;
    private String provider;
}
