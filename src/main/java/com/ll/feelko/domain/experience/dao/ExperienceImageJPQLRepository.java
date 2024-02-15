package com.ll.feelko.domain.experience.dao;


import com.ll.feelko.domain.experience.entity.ExperienceImage;
import com.ll.feelko.domain.experience.entity.QExperienceImage;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.ll.feelko.domain.experience.entity.QExperience.*;
import static com.ll.feelko.domain.experience.entity.QExperienceImage.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ExperienceImageJPQLRepository {

    private final JPQLQueryFactory query;


    @Transactional(readOnly = true)
    public ExperienceImage getImages(Long experienceId) {

        return query.selectFrom(experienceImage)
                .join(experienceImage.experience, experience)
                .where(experienceIdEquals(experienceId)).fetchOne();
    }


    private BooleanExpression experienceIdEquals(Long experienceId) {
        return experienceId != null ? QExperienceImage.experienceImage.experience.id.eq(experienceId) : null;
    }

}
