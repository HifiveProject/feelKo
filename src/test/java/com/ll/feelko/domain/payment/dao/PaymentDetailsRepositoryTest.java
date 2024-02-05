package com.ll.feelko.domain.payment.dao;

import com.ll.feelko.domain.experience.dao.ExperienceRepository;
import com.ll.feelko.domain.experience.entity.Experience;
import com.ll.feelko.domain.member.dao.MemberRepository;
import com.ll.feelko.domain.member.entity.Member;
import com.ll.feelko.domain.payment.api.response.TossPaymentResponse;
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
import java.util.List;
import java.util.stream.Collectors;

import static com.ll.feelko.global.common.entity.PaymentStatus.COMPLETE_PAYMENT;


@Slf4j
@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
class PaymentDetailsRepositoryTest {

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;

    @Autowired
    private MemberRepository memberRepository;

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

        Member userB = Member.builder()
                .name("userB")
                .email("ㅁㅁㅁ@naver.com")
                .password("userB")
                .profile("ooo.png")
                .phone("010-2345-1234")
                .birthday(LocalDate.now())
                .roles("ROLE_MEMBER")
                .build();

        //user save
        memberRepository.save(userA);
        memberRepository.save(userB);

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

        Experience secondExperience = Experience.builder()
                .memberId(userA.getId())
                .title("제목")
                .price(new BigDecimal(300000))
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .headcount(25L)
                .wishCounter(15L)
                .imageUrl("https://www.localnaeil.com/FileData/Article/201705/4e808dfd-795e-4671-b596-7e64e22d868a.jpg")
                .descriptionText("서술")
                .wishLists(new ArrayList<>())
                .build();

        Experience experience = Experience.builder()
                .memberId(userB.getId())
                .title("제목")
                .price(new BigDecimal(450000))
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .headcount(5L)
                .wishCounter(5L)
                .imageUrl("https://www.localnaeil.com/FileData/Article/201705/4e808dfd-795e-4671-b596-7e64e22d868a.jpg")
                .descriptionText("서술")
                .wishLists(new ArrayList<>())
                .build();

        //userA 결제 정보
        Payment paymentsFirst = Payment.builder()
                .member(userA)
                .experience(firstExperience)
                .email(userA.getEmail())
                .status(COMPLETE_PAYMENT)
                .headCount(4L)
                .paymentKey("Payment Key 2")
                .price(new BigDecimal(250000))
                .reservationDate(LocalDate.now()).build();

        Payment paymentsSecond = Payment.builder()
                .member(userA)
                .experience(secondExperience)
                .email(userA.getEmail())
                .status(COMPLETE_PAYMENT)
                .headCount(2L)
                .paymentKey("Payment Key 2")
                .price(new BigDecimal(300000))
                .reservationDate(LocalDate.now()).build();

        //userB 결제 정보
        Payment payment = Payment.builder()
                .member(userB)
                .email(userB.getEmail())
                .experience(secondExperience)
                .status(COMPLETE_PAYMENT)
                .headCount(10L)
                .paymentKey("Payment Key userB")
                .price(new BigDecimal(450000))
                .reservationDate(LocalDate.now()).build();

        //experience save
        experienceRepository.save(firstExperience);
        experienceRepository.save(secondExperience);
        experienceRepository.save(experience);

        //payment save
        paymentRepository.save(paymentsFirst);
        paymentRepository.save(paymentsSecond);
        paymentRepository.save(payment);
    }

    @Test
    @DisplayName("결제 상세조회 페이지")
    public void findPayment() {

        //give
        final Long memberId = 1L;
        final String userEmail = "xxx@naver.com";

        //when
        List<TossPaymentResponse> paymentResponses = paymentDetailsRepository.getAllReservations(memberId);

        List<String> emails = paymentResponses.stream()
                .map(TossPaymentResponse::getEmail)
                .collect(Collectors.toList());

        List<Long> headCounts = paymentResponses.stream()
                .map(TossPaymentResponse::getHeadCount)
                .collect(Collectors.toList());


        List<Experience> experiences = paymentResponses.stream()
                .map(TossPaymentResponse::getExperience)
                .toList();


        //then
        Assertions.assertThat(paymentResponses).isNotEmpty();
        Assertions.assertThat(emails).contains(userEmail);
        Assertions.assertThat(headCounts.get(0)).isEqualTo(4);
        Assertions.assertThat(headCounts.get(1)).isEqualTo(2);

    }

    @AfterEach
    public void deleteAll() {
        memberRepository.deleteAll();
        experienceRepository.deleteAll();
        paymentRepository.deleteAll();
    }
}