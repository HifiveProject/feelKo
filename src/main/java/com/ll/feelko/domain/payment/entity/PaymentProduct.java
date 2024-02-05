package com.ll.feelko.domain.payment.entity;

import com.ll.feelko.domain.experience.entity.Experience;
import com.ll.feelko.domain.member.entity.Member;
import com.ll.feelko.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentProduct extends BaseEntity {


    @Id
    @ManyToOne
    @JoinColumn(name = "experience_id")
    private Experience experience;

    @Id
    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    public PaymentProduct(Experience experience, Payment payment) {
        this.experience = experience;
        this.payment = payment;
    }

}
