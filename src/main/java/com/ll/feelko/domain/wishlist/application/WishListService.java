package com.ll.feelko.domain.wishlist.application;

import com.ll.feelko.domain.wishlist.dto.WishListDto;
import com.ll.feelko.domain.wishlist.dto.WishListSaveDto;
import org.springframework.data.domain.Page;

public interface WishListService{

    boolean saveWish(WishListSaveDto wishListSaveDto);

    Page<WishListDto> getWishList(long memberId, int page, int size);
}
