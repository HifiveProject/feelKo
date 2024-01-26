package com.ll.feelko.domain.member.application;

import com.ll.feelko.domain.member.dto.SocialLoginDto;
import com.ll.feelko.domain.member.entity.Member;
import com.ll.feelko.global.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberService memberService;

    // 카카오톡 로그인이 성공할 때 마다 이 함수가 실행된다.
    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String providerTypeCode = userRequest.getClientRegistration().getRegistrationId().toUpperCase();

        SocialLoginDto socialLoginDto = null;

        switch (providerTypeCode) {
            case "KAKAO" :
                socialLoginDto = extractKakaoData(providerTypeCode, oAuth2User);
                break;
            case "GOOGLE":
                socialLoginDto = extractGoogleData(providerTypeCode, oAuth2User);
                break;
            case "GITHUB":
                socialLoginDto = extractGitHubData(providerTypeCode, oAuth2User);
                break;
        }

        Member member = memberService.whenSocialLogin(socialLoginDto);

        return new SecurityUser(member.getId(), member.getEmail(), member.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + member.getRoles())));
    }

    //구글 데이터 추출
    public SocialLoginDto extractGoogleData(String providerTypeCode, OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = (String) attributes.get("email");
        String profileImageUrl = (String) attributes.get("picture");
        String oauthId = oAuth2User.getName();
        String nickname = (String) attributes.get("name");
        String providerId = providerTypeCode + "__%s".formatted(oauthId); // 비밀번호로 설정

        return new SocialLoginDto(providerTypeCode, email, profileImageUrl, providerId, nickname);
    }
    //깃허브 데이터 추출
    public SocialLoginDto extractGitHubData(String providerTypeCode, OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String profileImageUrl = (String) attributes.get("avatar_url");
        String oauthId = oAuth2User.getName();
        String nickname = (String) attributes.get("login");
        String providerId = providerTypeCode + "__%s".formatted(oauthId); // 비밀번호로 설정
        String email = (String) attributes.get("email");
        if(email == null) email = nickname + "@" + providerTypeCode;

        return new SocialLoginDto(providerTypeCode, email, profileImageUrl, providerId, nickname);
    }
    //카카오 데이터 추출
    public SocialLoginDto extractKakaoData(String providerTypeCode, OAuth2User oAuth2User) {
        String oauthId = oAuth2User.getName();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map attributesProperties = (Map) attributes.get("properties");

        String nickname = (String) attributesProperties.get("nickname");
        String profileImageUrl = (String) attributesProperties.get("profile_image");
        String providerId = providerTypeCode + "__" + oauthId;
        String email = nickname + "@" + providerTypeCode;

        return new SocialLoginDto(providerTypeCode, email, profileImageUrl, providerId, nickname);
    }
}

