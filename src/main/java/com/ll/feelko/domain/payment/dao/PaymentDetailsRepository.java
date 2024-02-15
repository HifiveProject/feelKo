package com.ll.feelko.domain.payment.dao;

import com.ll.feelko.domain.payment.api.response.TossPaymentResponse;
import com.ll.feelko.domain.payment.dto.PaymentDetailDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ll.feelko.domain.experience.entity.QExperience.experience;
import static com.ll.feelko.domain.member.entity.QMember.member;
import static com.ll.feelko.domain.payment.entity.QPayment.payment;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PaymentDetailsRepository {

    private final JPQLQueryFactory query;

    @Transactional(readOnly = true)
    public PaymentDetailDto getPaymentDetail(Long paymentId) {

        PaymentDetailDto fetch = query.select(Projections.fields(PaymentDetailDto.class,
                        member.name,
                        experience.title,
                        member.email,
                        payment.reservationDate,
                        payment.headCount,
                        payment.paymentKey,
                        payment.price))
                .from(payment)
                .leftJoin(payment.member, member)
                .leftJoin(payment.experience, experience)
                .where(payment.id.eq(paymentId))
                .fetchOne();

        return fetch;
    }


}