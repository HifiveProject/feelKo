package com.ll.feelko.domain.experience.dao;

import com.ll.feelko.domain.experience.entity.Experience;
import com.ll.feelko.domain.member.dto.UploadedPageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience,Long> {

    @Query("SELECT new com.ll.feelko.domain.member.dto.UploadedPageDto(e.id, e.title, e.price, e.startDate, e.endDate) FROM Experience e WHERE e.memberId = :memberId ORDER BY e.id DESC")
    Page<UploadedPageDto> findIdTitleByMemberIdOrderByIdDesc(@Param("memberId") long memberId, Pageable pageable);

    @Query("SELECT e FROM Experience e ORDER BY e.startDate DESC")
    Page<Experience> searchExperiencesAll(Pageable pageable);

    @Query("SELECT e FROM Experience e WHERE e.wishCounter >= 1 AND e.experienceClose = false ORDER BY e.wishCounter DESC")
    List<Experience> findPopularExperiences(Pageable pageable);

    List<Experience> findByExperienceCloseFalse();
    @Query("SELECT e FROM Experience e")
    Page<Experience> searchAllExperiencesIncludingClosing(Pageable pageable);

    @Query("SELECT e FROM Experience e WHERE e.location = :destination AND (:startDate IS NULL OR e.startDate = :startDate)")
    Page<Experience> searchExperiencesIncludingClosing(String destination, LocalDate startDate, Pageable pageable);

    @Query("SELECT e FROM Experience e WHERE e.location = :destination AND e.startDate <= :selectDate AND e.endDate >= :selectDate AND e.experienceClose = false")
    Page<Experience> findByDateRangeAndLocation(@Param("destination") String destination, @Param("selectDate") LocalDate selectDate, Pageable pageable);

    @Query("SELECT e FROM Experience e WHERE e.location = :destination AND e.experienceClose = false")
    Page<Experience> findByLocation(@Param("destination") String destination, Pageable pageable);

    Page<Experience> findByExperienceCloseFalse(Pageable pageable);
}
