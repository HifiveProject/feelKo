package com.ll.feelko.domain.payment.dao;

import com.ll.feelko.domain.member.dto.ReservationDto;
import com.ll.feelko.domain.member.dto.UploadReservationDto;
import com.ll.feelko.domain.payment.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("""
            SELECT new com.ll.feelko.domain.member.dto.UploadReservationDto(m.name, m.phone, m.email,m.profile, p.reservationDate)
            FROM Payment p 
            LEFT JOIN p.member m 
            WHERE p.experience.id = :experienceId AND p.deletedAt IS NULL
            ORDER BY p.reservationDate DESC
            """)
    List<UploadReservationDto> findByExperienceIdWithMemberInfo(@Param("experienceId") Long experienceId);

    @Query("""
            SELECT new com.ll.feelko.domain.member.dto.ReservationDto(e.imageUrl, e.title, e.id, p.paymentKey, p.price, p.reservationDate)
            FROM Payment p 
            LEFT JOIN p.experience e 
            WHERE p.member.id = :memberId AND p.deletedAt IS NULL
            ORDER BY p.createdAt DESC         
            """)
    Page<ReservationDto> findByMemberIdOrderByCreatedAtDescWithExperience(@Param("memberId") Long memberId, Pageable pageable);

}
