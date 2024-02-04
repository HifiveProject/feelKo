package com.ll.feelko.domain.wishlist.api;

import com.ll.feelko.domain.wishlist.application.WishListService;
import com.ll.feelko.domain.wishlist.dto.WishListDto;
import com.ll.feelko.domain.wishlist.dto.WishListPageDto;
import com.ll.feelko.global.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member/mypage")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class WishListController {
    private final WishListService wishListService;


    // 찜 목록 열람
    @GetMapping("/wishlist")
    public String showWishList(@AuthenticationPrincipal SecurityUser user,
                               @RequestParam(name = "page", defaultValue = "0") int page,
                               @RequestParam(name = "size", defaultValue = "10") int size,
                               Model model) {
        Page<WishListPageDto> wishListDtos = wishListService.getWishList(user.getId(), page, size);
        model.addAttribute("wishLists", wishListDtos);
        return "domain/member/mypage/wishlist";
    }

    // 찜 기능
    @PostMapping("/wishlist")
    @ResponseBody
    public ResponseEntity<?> saveWish(@RequestParam("experienceId") Long experienceId, @AuthenticationPrincipal SecurityUser user) {
        WishListDto wishListDto = new WishListDto(user.getId(), experienceId);

        return wishListService.saveWish(wishListDto)
                ? ResponseEntity.ok(true)
                : ResponseEntity.ok(false);
    }

}
