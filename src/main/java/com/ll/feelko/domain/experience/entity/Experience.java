package com.ll.feelko.domain.experience.entity;

import com.ll.feelko.domain.wishlist.entity.WishList;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
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

    private Long memberId;

    private String title;

    @Comment("금액")
    @Column(name = "price")
    private BigDecimal price;

    @Comment("시작 날짜")
    @Column(name = "start_date")
    private LocalDate startDate;

    @Comment("종료 날짜")
    @Column(name = "end_date")
    private LocalDate endDate;

    @Comment("인원수")
    @Column(name = "headcount")
    private Long headcount;

    @Builder.Default
    @Comment("좋아요")
    @Column(name = "wish_counter")
    private Long wishCounter = 0L;

    private String descriptionText;

    @Comment("초기 마감 인원수")
    @Column(name = "original_headcount")
    private Long originalHeadcount;

    private String location;

    @Comment("체험 마감")
    @Column(name = "experience_close")
    private Boolean experienceClose;

    private String answer;

    @OneToMany(mappedBy = "experience", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ExperienceImage> images;

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
            experienceClose = headcount==0 ? true : false;
        }
    }

    public boolean isClosingSoon() {
        // 현재 참가자 인원수가 1 이상이면서 70% 미만인 경우에 마감 임박으로 표시
        return this.headcount != null && this.headcount >= 1 && this.headcount < (0.3 * this.originalHeadcount);
    }

    public void addImage(ExperienceImage image) {
        if (this.images == null) {
            this.images = new ArrayList<>();
        }
        this.images.add(image);
        image.setExperience(this); // 양방향 연관관계 설정
    }

}