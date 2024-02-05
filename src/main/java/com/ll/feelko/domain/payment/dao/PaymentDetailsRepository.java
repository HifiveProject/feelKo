package com.ll.feelko.domain.payment.dao;

import com.ll.feelko.domain.payment.api.response.TossPaymentResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ll.feelko.domain.experience.entity.QExperience.*;
import static com.ll.feelko.domain.member.entity.QMember.*;
import static com.ll.feelko.domain.payment.entity.QPayment.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PaymentDetailsRepository {

    private final JPQLQueryFactory query;

    @Transactional(readOnly = true)
    public List<TossPaymentResponse> getAllReservations(Long memberId) {

        List<TossPaymentResponse> fetch = query.select(Projections.fields(TossPaymentResponse.class,
                        payment.email,
                        payment.status,
                        payment.experience,
                        payment.reservationDate,
                        payment.headCount,
                        payment.paymentKey,
                        payment.price))
                .from(payment)
                .join(payment.member, member)
                .join(payment.experience, experience)
                .where(member.id.eq(memberId))
                .fetch();

        return fetch;
    }



}