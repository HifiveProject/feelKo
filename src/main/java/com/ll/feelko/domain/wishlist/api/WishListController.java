package com.ll.feelko.domain.wishlist.api;

import com.ll.feelko.domain.member.application.MypageService;
import com.ll.feelko.domain.wishlist.application.WishListService;
import com.ll.feelko.domain.wishlist.dto.WishListDto;
import com.ll.feelko.domain.wishlist.dto.WishListSaveDto;
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
    private final MypageService mypageService;

//    @GetMapping("/wishlist")
//    public String showWish(){
//        return "domain/member/mypage/wishlist";
//    }


    @GetMapping("/wishlist")
    public String showWishList(@AuthenticationPrincipal SecurityUser user,
                                       @RequestParam(name = "page", defaultValue = "0") int page,
                                       @RequestParam(name = "size", defaultValue = "10") int size,
                                       Model model) {
        Page<WishListDto> wishListDtos = wishListService.getWishList(user.getId(), page, size);
        model.addAttribute("wishListDtos",wishListDtos);
        return "domain/member/mypage/wishlist";
    }

    @PostMapping("/wishlist")
    @ResponseBody
    public ResponseEntity<?> saveWish(@RequestBody WishListSaveDto wishListSaveDto) {

        return wishListService.saveWish(wishListSaveDto)
                ? ResponseEntity.ok(true)
                : ResponseEntity.ok(false);
    }

}
