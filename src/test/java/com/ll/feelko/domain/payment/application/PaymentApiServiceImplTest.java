package com.ll.feelko.domain.payment.application;

import com.ll.feelko.domain.experience.dao.ExperienceRepository;
import com.ll.feelko.domain.experience.entity.Experience;
import com.ll.feelko.domain.member.dao.MemberRepository;
import com.ll.feelko.domain.member.entity.Member;
import com.ll.feelko.domain.payment.dao.PaymentDetailsRepository;
import com.ll.feelko.domain.payment.dao.PaymentRepository;
import com.ll.feelko.domain.payment.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import static com.ll.feelko.global.common.entity.PaymentStatus.COMPLETE_PAYMENT;
import static org.junit.jupiter.api.Assertions.*;



@Slf4j
@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
class PaymentApiServiceImplTest {

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PaymentApiServiceImpl paymentApiService;


    @BeforeEach
    @DisplayName("사용자 체험 예약 신청")
    public void before() {

        Member userA = Member.builder()
                .name("userA")
                .email("xxx@naver.com")
                .password("userA")
                .profile("xxx.png")
                .phone("010-1234-1234")
                .birthday(LocalDate.now())
                .roles("ROLE_MEMBER")
                .build();

        //user save
        memberRepository.save(userA);


        Experience firstExperience = Experience.builder()
                .memberId(userA.getId())
                .title("제목")
                .price(new BigDecimal(250000))
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .headcount(10L)
                .wishCounter(10L)
                .imageUrl("https://www.localnaeil.com/FileData/Article/201705/4e808dfd-795e-4671-b596-7e64e22d868a.jpg")
                .descriptionText("서술")
                .wishLists(new ArrayList<>())
                .build();


        //experience save
        experienceRepository.save(firstExperience);

    }


    @Test
    @DisplayName("참가인원 만큼 인원수 감소")
    public void decreaseAttendanceByNumberOfParticipants() {

        //give
        Member memberA = memberRepository.findById(1L).orElseThrow();

        Experience experience = experienceRepository.findById(1L).orElseThrow();

        Payment payments  = Payment.builder()
                .member(memberA)
                .experience(experience)
                .email(memberA.getEmail())
                .status(COMPLETE_PAYMENT)
                .headCount(4L)
                .paymentKey("Payment Key 2")
                .price(new BigDecimal(250000))
                .reservationDate(LocalDate.now()).build();

        paymentRepository.save(payments);

        //when
        paymentApiService.decreaseParticipants(
                experience.getId(),
                payments.getHeadCount()
        );


        //then
        Assertions.assertThat(experience.getHeadcount()).isEqualTo(6);

    }

    @AfterEach
    public void deleteAll() {
        memberRepository.deleteAll();
        experienceRepository.deleteAll();
        paymentRepository.deleteAll();
    }
}