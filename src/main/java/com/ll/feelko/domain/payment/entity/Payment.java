package com.ll.feelko.domain.payment.entity;

import com.ll.feelko.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;




@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    private String status;

    private String orderId;

    private String paymentKey;

    private BigDecimal price;

    @Builder
    public Payment(Member member, String status, String orderId, String paymentKey, BigDecimal price) {
        this.member = member;
        this.status = status;
        this.orderId = orderId;
        this.paymentKey = paymentKey;
        this.price = price;
    }

}
