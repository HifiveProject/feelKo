package com.ll.feelko.domain.wishlist.dao;

import com.ll.feelko.domain.wishlist.dto.WishListDto;
import com.ll.feelko.domain.wishlist.entity.WishList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishList, Long> {
    Optional<WishList> findByMemberIdAndExperienceId(Long memberId, Long experienceId);

    @Query("SELECT new com.ll.feelko.domain.wishlist.dto.WishListDto(w.member.id, w.experience.title) " +
            "FROM WishList w " +
            "WHERE w.member.id = :memberId " +
            "ORDER BY w.id DESC")
    Page<WishListDto> findIdTitleByMemberIdOrderByIdDesc(@Param("memberId") Long memberId, Pageable pageable);
}
