package com.ll.feelko.domain.experience.entity;

import com.ll.feelko.domain.wishlist.entity.WishList;
import jakarta.persistence.*;
import lombok.*;

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

    private Long memberId; //업로드한 사용자 Fk

    private String title; //제목

    private BigDecimal price; //가격

    private LocalDate startDate;//시작 날짜

    private LocalDate endDate;//종료 날짜

    private Long headcount; //인원수

    @Builder.Default
    private Long wishCounter = 0L;//좋아요

    private String descriptionText;

    private Long originalHeadcount; // 초기 마감 인원수

    private String location;//장소

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