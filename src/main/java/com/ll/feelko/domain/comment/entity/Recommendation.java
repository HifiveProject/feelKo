package com.ll.feelko.domain.comment.entity;

import com.ll.feelko.domain.experience.entity.Experience;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Recommendation {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "experience_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Experience experience;

    //작성 날짜 LocalDate.now
    private LocalDate createDate;

    //내용
    private String comment;

    //추천 평점 1 ~ 5
    private Long recommendation;
}
