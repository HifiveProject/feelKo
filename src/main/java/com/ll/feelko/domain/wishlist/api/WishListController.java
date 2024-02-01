package com.ll.feelko.domain.wishlist.api;

import com.ll.feelko.domain.wishlist.application.WishListService;
import com.ll.feelko.domain.wishlist.dto.WishListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member/mypage")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class WishListController {
    private final WishListService wishListService;

    @PostMapping("/wishlist")
    public ResponseEntity<?> saveWish(@RequestBody WishListDto wishListDto) {

        return wishListService.saveWish(wishListDto)
                ? ResponseEntity.ok(true)
                : ResponseEntity.ok(false);
    }

}
