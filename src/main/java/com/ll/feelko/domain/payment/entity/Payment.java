package com.ll.feelko.domain.payment.entity;

import com.ll.feelko.domain.experience.entity.Experience;
import com.ll.feelko.domain.member.entity.Member;
import com.ll.feelko.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Entity
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

    private String status;

    private String orderId;

    private String paymentKey;

    private BigDecimal price;

    private LocalDate reservationDate;
}
