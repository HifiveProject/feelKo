package com.ll.feelko.domain.payment.entity;

import com.ll.feelko.domain.experience.entity.Experience;
import com.ll.feelko.domain.member.entity.Member;
import com.ll.feelko.global.common.entity.BaseEntity;
import com.ll.feelko.global.common.entity.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Entity
@ToString
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "experience_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Experience experience;

    private String email;

    private PaymentStatus status;

    private Long headCount;

    private String paymentKey;

    private BigDecimal price;

    private LocalDate reservationDate;

}
