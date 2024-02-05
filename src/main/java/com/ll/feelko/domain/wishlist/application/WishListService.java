package com.ll.feelko.domain.wishlist.application;

import com.ll.feelko.domain.wishlist.dto.WishListDto;
import com.ll.feelko.domain.wishlist.dto.WishListPageDto;
import com.ll.feelko.global.security.SecurityUser;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface WishListService{

    boolean saveWish(WishListDto wishListDto);

    boolean checkWish(WishListDto wishListDto);

    Page<WishListPageDto> getWishList(long memberId, int page, int size);

    Optional<WishListDto> createWishListDtoIfLogined(SecurityUser user, Long experienceId);
}
