package com.ll.feelko.domain.experience.entity;

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

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
//    private Member member;
    private Long memberId; //업로드한 사용자 Fk

    private String title; //제목

    private BigDecimal price; //가격

    private LocalDate startDate;

    private LocalDate endDate;

    private Long headcount; //인원수

    @Builder.Default
    private Long wishCounter = 0L;

    private String imageUrl;

    private String descriptionText;

    private Long originalHeadcount; // 초기 마감 인원수

    private String location;

    private Boolean experienceClose;

    private String answer;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "experience")
    private List<Answer> answers;

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

    //인원수 감소
    public void headcountReduction(Long decrease) {
        if (decrease == null || decrease < 0) {
            throw new IllegalArgumentException("참가자 인원을 확인해주세요");
        }
        if (this.headcount - decrease != this.headcount) {
            this.headcount = this.headcount - decrease;
        }
    }

    public void headcountUpdate(Long headcountUpdate) {
        this.headcount = headcountUpdate;
    }

    public boolean isClosingSoon() {
        // 현재 참가자 인원수가 1 이상이면서 70% 미만인 경우에 마감 임박으로 표시
        return this.headcount != null && this.headcount >= 1 && this.headcount < (0.3 * this.originalHeadcount);
    }
    // 체험을 생성할 때 초기 마감 인원수를 기록
    public void setOriginalHeadcount(Long headcount) {
        this.originalHeadcount = headcount;
    }

}
