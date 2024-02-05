package com.ll.feelko.domain.experience.entity;

import com.ll.feelko.domain.member.entity.Member;
import com.ll.feelko.domain.wishlist.entity.WishList;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Entity
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    private String title;

    private BigDecimal price;

    private LocalDate startDate;

    private LocalDate endDate;

    private Long headcount;

    @Builder.Default
    private Long wishCounter = 0L;

    private String imageUrl;

    private String descriptionText;

    private String location;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "experience")
    private List<WishList> wishLists;

    // 해당 체험의 찜 수 증가
    public void increaseCount() {
        this.wishCounter++;
    }

    // 해당 체험의 찜 수 감소
    public void decreaseCount() {
        if (this.wishCounter > 0) {
            this.wishCounter--;
        }
    }


}
