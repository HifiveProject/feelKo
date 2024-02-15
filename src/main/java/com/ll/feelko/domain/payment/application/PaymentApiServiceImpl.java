package com.ll.feelko.domain.payment.application;


import com.ll.feelko.domain.experience.dao.ExperienceRepository;
import com.ll.feelko.domain.experience.entity.Experience;
import com.ll.feelko.domain.member.dao.MemberRepository;
import com.ll.feelko.domain.member.entity.Member;
import com.ll.feelko.domain.payment.dao.PaymentDetailsRepository;
import com.ll.feelko.domain.payment.dao.PaymentRepository;
import com.ll.feelko.domain.payment.dto.PaymentDetailDto;
import com.ll.feelko.domain.payment.entity.Payment;
import com.ll.feelko.global.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static com.ll.feelko.global.common.entity.PaymentStatus.*;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentApiServiceImpl implements PaymentApiService {

    private final PaymentRepository paymentRepository;
    private final PaymentDetailsRepository paymentDetailsRepository;
    private final ExperienceRepository experienceRepository;
    private final MemberRepository memberRepository;


    @Override
    @Transactional
    public void payment(
            SecurityUser member,
            Long experienceId,
            Long headcount,
            LocalDate reservationDate,
            String paymentKey,
            BigDecimal amount) {
        Member memberInformation = getMemberInformation(member.getId());

        //Experience experienceId = decreaseParticipants(experience, headcount);
        Experience experience = experienceRepository.findById(experienceId).orElseThrow(() -> new RuntimeException("체험 찾을수 없어 익셉션"));
        if (experience.getHeadcount() < headcount) {
            throw new RuntimeException("인원 너무 많아 익셉션");
        }

        LocalDate start = experience.getStartDate();
        LocalDate end = experience.getEndDate();

        if (start.isAfter(reservationDate) || reservationDate.isAfter(end)) throw new RuntimeException("날짜 확인해 익셉션");

        Payment payment = Payment.builder()
                .member(memberInformation)
                .experience(experience)
                .email(memberInformation.getEmail())
                .status(COMPLETE_PAYMENT)
                .headCount(headcount)
                .paymentKey(paymentKey)
                .price(amount)
                .reservationDate(reservationDate).build();

        paymentRepository.save(payment);
    }

    public Member getMemberInformation(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow();
    }

    @Override
    @Transactional
    public Experience decreaseParticipants(Long getExperiencesId,
                                           Long headcount

    ) {
        Experience experience =
                experienceRepository
                        .findById(getExperiencesId)
                        .orElseThrow();

        experience.headcountReduction(headcount);

        return experience;
    }

    @Override
    public PaymentDetailDto findPaymentDetailByPaymentId(Long paymentId) {
        return paymentDetailsRepository.getPaymentDetail(paymentId);
        //null체크를 isMyPayment에서 함
    }

    @Override
    public boolean isMyPayment(Long memberId, Long paymentId) {
        Optional<Payment> payment = paymentRepository.findById(paymentId);
        if (payment.isEmpty()) throw new RuntimeException("결제 정보가 존재하지 않습니다.");
        return payment.get().getMember().getId() == memberId;
        //TODO 쿼리로 member.id만 가져오도록 할지 강사님께 여쭤보기
    }
}
