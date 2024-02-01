package com.ll.feelko.domain.experience.dao;

import com.ll.feelko.domain.experience.entity.Experience;
import com.ll.feelko.domain.member.dto.uploadedPageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExperienceRepository extends JpaRepository<Experience,Long> {

    @Query("SELECT new com.ll.feelko.domain.member.dto.uploadedPageDto(e.id, e.title) FROM Experience e WHERE e.member.id = :memberId ORDER BY e.id DESC")
    Page<uploadedPageDto> findIdTitleByMemberIdOrderByIdDesc(@Param("memberId") long memberId, Pageable pageable);

}
