package com.ll.feelko.domain.wishlist.dao;

import com.ll.feelko.domain.wishlist.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishList, Long> {
    Optional<WishList> findByMemberIdAndExperienceId(Long memberId, Long experienceId);
}
