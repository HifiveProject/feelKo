package com.ll.feelko.domain.experience.entity;

import com.ll.feelko.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

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


    private String imageUrl;


    private Long headcount;


    private String location;
}
