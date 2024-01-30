package com.ll.feelko.global.init;

import com.ll.feelko.domain.experience.dao.ExperienceRepository;
import com.ll.feelko.domain.experience.entity.Experience;
import com.ll.feelko.domain.experience.application.ExperienceService;
import com.ll.feelko.domain.experience.dto.ExperienceCreateDTO;
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

import java.util.List;
import java.util.stream.IntStream;

@Configuration
@Profile("!prod")
@Slf4j
@RequiredArgsConstructor
public class NotProd {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final ExperienceService experienceService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public ApplicationRunner initNotProd() {
        return args -> {

            if (memberService.getMemberCount() != 0) {
                return;
            }

            //테스트용 멤버 생성
            //admin생성  role때문에 그냥 엔티티로 만듬
            Member admin = Member.builder()
                    .email("admin")
                    .password(passwordEncoder.encode("admin"))
                    .roles("admin")
                    .build();
            memberRepository.save(admin);


            List<Member> members = IntStream.rangeClosed(1, 5)
                    .mapToObj(i -> {
                        MemberRegisterDto memberRegisterDto = new MemberRegisterDto(
                                "test", "test", "test", null, "010-1111-1111", null, null);
                        memberRegisterDto.setEmail("test" + i);

                        return memberService.register(memberRegisterDto);
                    })
                    .toList();

            // 체험 테스트 데이터 생성
            members.forEach(member -> IntStream.rangeClosed(1, 10).forEach(i -> {
                experienceService.createExperience(ExperienceCreateDTO.builder()
                        .memberId(member.getId())
                        .title("title" + i)
                        .imageFile(null)
                        .location("장소" + i)
                        .build());
            }));
        };
    }
}
