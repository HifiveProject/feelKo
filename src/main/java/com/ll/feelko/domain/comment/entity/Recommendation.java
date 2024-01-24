package com.ll.feelko.domain.comment.entity;

import com.ll.feelko.domain.experience.entity.Experience;
import jakarta.persistence.*;
import lombok.*;


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

    private String comment;


    private Long recommendation;
}
