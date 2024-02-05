package com.ll.feelko.domain.experience.dao;

import com.ll.feelko.domain.experience.entity.Experience;
import com.ll.feelko.domain.member.dto.UploadedPageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExperienceRepository extends JpaRepository<Experience,Long> {

    @Query("SELECT new com.ll.feelko.domain.member.dto.UploadedPageDto(e.id, e.title) FROM Experience e WHERE e.memberId = :memberId ORDER BY e.id DESC")
    Page<UploadedPageDto> findIdTitleByMemberIdOrderByIdDesc(@Param("memberId") long memberId, Pageable pageable);

    @Query("SELECT e FROM Experience e WHERE e.location = :destination")
    Page<Experience> searchExperiences(String destination, Pageable pageable);

    @Query("SELECT e FROM Experience e")
    Page<Experience> searchExperiencesAll(Pageable pageable);
}
