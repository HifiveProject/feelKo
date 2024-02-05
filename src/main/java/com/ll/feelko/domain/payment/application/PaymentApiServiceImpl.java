package com.ll.feelko.domain.payment.application;


import com.ll.feelko.domain.experience.dao.ExperienceRepository;
import com.ll.feelko.domain.experience.entity.Experience;
import com.ll.feelko.domain.payment.dao.PaymentDetailsRepository;
import com.ll.feelko.domain.payment.dao.PaymentRepository;
import com.ll.feelko.domain.payment.dto.PaymentDetailDto;
import com.ll.feelko.domain.payment.entity.Payment;
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
public class PaymentApiServiceImpl implements PaymentApiService{

    private final PaymentRepository paymentRepository;
    private final PaymentDetailsRepository paymentDetailsRepository;
    private final ExperienceRepository experienceRepository;


    @Override
    @Transactional
    public void payment(String userEmail,
                        Long headcount,
                        LocalDate reservationDate,
                        String paymentKey,
                        BigDecimal amount) {

        Payment payment = Payment.builder()
                .email(userEmail)
                .status(COMPLETE_PAYMENT)
                .headCount(headcount)
                .paymentKey(paymentKey)
                .price(amount)
                .reservationDate(reservationDate).build();

        paymentRepository.save(payment);
    }


    @Override
    @Transactional
    public void decreaseParticipants(Long getExperiencesId,
                                     Long headcount

    ) {
        Experience experience =
                experienceRepository
                        .findById(getExperiencesId)
                        .orElseThrow();

        experience.headcountReduction(headcount);

    }

    @Override
    public PaymentDetailDto findPaymentDetailByPaymentId(Long paymentId){
        return paymentDetailsRepository.getPaymentDetail(paymentId);
        //null체크를 isMyPayment에서 함
    }

    @Override
    public boolean isMyPayment(Long memberId, Long paymentId){
        Optional<Payment> payment = paymentRepository.findById(paymentId);
        if(payment.isEmpty()) throw new RuntimeException("결제 정보가 존재하지 않습니다.");
        return payment.get().getMember().getId() == memberId;
        //TODO 쿼리로 member.id만 가져오도록 할지 강사님께 여쭤보기
    }
}
