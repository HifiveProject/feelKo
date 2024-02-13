package com.ll.feelko.global.init;

import com.ll.feelko.domain.experience.application.ExperienceService;
import com.ll.feelko.domain.experience.dto.ExperienceCreateDTO;
import com.ll.feelko.domain.experience.entity.Experience;
import com.ll.feelko.domain.member.application.MemberService;
import com.ll.feelko.domain.member.dao.MemberRepository;
import com.ll.feelko.domain.member.dto.MemberRegisterDto;
import com.ll.feelko.domain.member.entity.Member;
import com.ll.feelko.domain.payment.dao.PaymentRepository;
import com.ll.feelko.domain.payment.entity.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    private final PaymentRepository paymentRepository;

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
                    .roles("ADMIN")
                    .status("complete")
                    .build();
            memberRepository.save(admin);


            List<Member> members = IntStream.rangeClosed(1, 5)
                    .mapToObj(i -> {
                        MemberRegisterDto memberRegisterDto = new MemberRegisterDto(
                                "test", "test", "test", null, "010-1111-1111", null, null, "complete");
                        memberRegisterDto.setEmail("test" + i + "@example.com");

                        return memberService.register(memberRegisterDto);
                    })
                    .toList();

            // 체험 테스트 데이터 생성
            members.forEach(member -> IntStream.rangeClosed(1, 20).forEach(i -> {
                experienceService.createExperience(ExperienceCreateDTO.builder()
                        .memberId(member.getId())
                        .title("title" + i)
                        .startDate(LocalDate.now())
                        .endDate(LocalDate.now())
                        .price(new BigDecimal(1000000))
                        .imageFiles(null)
                        .location("서울")
                        .experienceClose(false)
                        .descriptionText("내용" + i)
                        .headcount(10L)
                        .build());
            }));

            members.forEach(member -> IntStream.rangeClosed(1, 10).forEach(i -> {
                experienceService.createExperience(ExperienceCreateDTO.builder()
                        .memberId(member.getId())
                        .title("title" + i)
                        .startDate(LocalDate.now())
                        .endDate(LocalDate.now())
                        .price(new BigDecimal(1000000))
                        .imageFiles(null)
                        .location("인천")
                        .experienceClose(true)
                        .descriptionText("내용" + i)
                        .headcount(10L)
                        .build());
            }));

            //테스트 결제 정보 데이터 생성
            Experience experience = experienceService.findByIdElseThrow(10L);
            Member member = memberService.findByIdElseThrow(3L);
            for (int i = 0; i < 10; i++) {
                Payment payment = Payment.builder()
                        .paymentKey("1")
                        .price(new BigDecimal(1))
                        .paymentKey("1")
                        .member(member)
                        .experience(experience)
                        .reservationDate(LocalDate.now())
                        .experience(experience)
                        .build();
                paymentRepository.save(payment);
            }
            for (int i = 0; i < 10; i++) {
                Payment payment = Payment.builder()
                        .paymentKey("1")
                        .price(new BigDecimal(1))
                        .paymentKey("1")
                        .member(member)
                        .experience(experience)
                        .reservationDate(LocalDate.now().minusDays(1))
                        .experience(experience)
                        .build();
                paymentRepository.save(payment);
            }
            for (int i = 0; i < 10; i++) {
                Payment payment = Payment.builder()
                        .paymentKey("1")
                        .price(new BigDecimal(1))
                        .paymentKey("1")
                        .member(member)
                        .experience(experience)
                        .reservationDate(LocalDate.now().minusDays(2))
                        .experience(experience)
                        .build();
                paymentRepository.save(payment);
            }
        };
    }
}
