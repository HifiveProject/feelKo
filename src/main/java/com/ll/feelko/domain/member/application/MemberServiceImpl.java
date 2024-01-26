package com.ll.feelko.domain.member.application;


import com.ll.feelko.domain.member.dao.MemberRepository;
import com.ll.feelko.domain.member.dto.MemberRegisterDto;
import com.ll.feelko.domain.member.dto.SocialLoginDto;
import com.ll.feelko.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Member register(MemberRegisterDto registerDto) {
        if (emailIsExist(registerDto.getEmail())) {
            //중복 검사
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }
        Member member = Member.builder()
                .email(registerDto.getEmail())
                .name(registerDto.getName())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .profile(registerDto.getProfile())
                .phone(registerDto.getPhone())
                .birthday(registerDto.getBirthday())
                .providerId(registerDto.getProviderId())
                .roles("user") // 그냥 string으로 할지 Grantedauthority로 할지
                .build();
        memberRepository.save(member);

        return member;
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public boolean emailIsExist(String email){
        return memberRepository.existsByEmail(email);
    }

    @Override
    public long getMemberCount(){
        return memberRepository.count();
    }

    @Override
    public Optional<Member> findById(Long id){
        return memberRepository.findById(id);
    }

    @Transactional
    public Member whenSocialLogin(SocialLoginDto socialLoginDto) {
        Optional<Member> optMember = findByProviderId(socialLoginDto.getProviderId());

        if (optMember.isPresent()) return optMember.get();

        //SocialLoginDto to MemberRegisterDto
        MemberRegisterDto registerDto = new MemberRegisterDto(
                socialLoginDto.getEmail(),
                socialLoginDto.getProviderId(), //비밀번호 수정예정
                socialLoginDto.getNickname(),
                socialLoginDto.getProfileImageUrl(),
                null,
                null,
                socialLoginDto.getProviderId()
        );

        return register(registerDto);
    }

    private Optional<Member> findByProviderId(String providerId) {
        return memberRepository.findByProviderId(providerId);
    }

}
