package com.ll.feelko.domain.experience.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.List;



@Getter
@ToString
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ExperienceImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(JsonType.class)
    @Column(name = "image", columnDefinition ="json")
    private List<String> image;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "experience_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Experience experience;

    public void setExperience(Experience experience) {
        this.experience = experience;
    }
}
