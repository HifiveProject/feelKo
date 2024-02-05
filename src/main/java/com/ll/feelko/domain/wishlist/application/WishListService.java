package com.ll.feelko.domain.wishlist.application;

import com.ll.feelko.domain.wishlist.dto.WishListDto;
import com.ll.feelko.domain.wishlist.dto.WishListPageDto;
import org.springframework.data.domain.Page;

public interface WishListService{

    boolean saveWish(WishListDto wishListDto);

    boolean checkWish(WishListDto wishListDto);

    Page<WishListPageDto> getWishList(long memberId, int page, int size);

}
