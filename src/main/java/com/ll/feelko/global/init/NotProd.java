package com.ll.feelko.global.init;

import com.ll.feelko.domain.experience.dao.ExperienceRepository;
import com.ll.feelko.domain.experience.entity.Experience;
import com.ll.feelko.domain.member.application.MemberService;
import com.ll.feelko.domain.member.dao.MemberRepository;
import com.ll.feelko.domain.member.dto.MemberRegisterDto;
import com.ll.feelko.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@Profile("!prod")
@Slf4j
@RequiredArgsConstructor
public class NotProd {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final ExperienceRepository experienceRepository;
    private final PasswordEncoder passwordEncoder;
    @Bean
    public ApplicationRunner initNotProd() {
        return args -> {
            //테스트용 멤버 생성
            memberInit();

            //여기부터 추가하시면 됩니다.


        };
    }

    @Transactional
    public void memberInit() {
        if (memberService.getMemberCount() == 0) {
            //admin생성  role때문에 그냥 엔티티로 만듬
            Member admin = Member.builder()
                    .email("admin")
                    .password(passwordEncoder.encode("admin"))
                    .roles("admin")
                    .build();
            memberRepository.save(admin);

            for (int i = 1; i <= 45; i++) {
                experienceRepository.save(
                        Experience.builder()
                        .member(admin)
                        .title("test"+ i)
                        .build()
                );
            }

            //테스트 멤버 생성 일단 당장 속도때문에 스트림은 사용하지 않았음
            MemberRegisterDto memberRegisterDto = new MemberRegisterDto(
                    "test", "test", "test", null, "010-1111-1111", null);

            for (int i = 1; i < 5; i++) {
                memberRegisterDto.setEmail("test" + i);
                memberService.register(memberRegisterDto);
            }
        }
    }

}
