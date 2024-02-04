package com.ll.feelko.domain.experience.api;

import com.ll.feelko.domain.experience.application.ExperienceService;
import com.ll.feelko.domain.wishlist.application.WishListService;
import com.ll.feelko.domain.wishlist.dto.WishListDto;
import com.ll.feelko.global.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/experiences")
public class ExperienceController {

    private final ExperienceService experienceService;
    private final WishListService wishListService;

    @GetMapping("/{experienceId}")
    public String detail(@PathVariable(name = "experienceId") Long experienceId, Model model, @AuthenticationPrincipal SecurityUser user) {
        WishListDto wishListCheckDto = new WishListDto(user.getId(), experienceId);

        model.addAttribute("experience", experienceService.detail(experienceId));
        model.addAttribute("isWished", wishListService.checkWish(wishListCheckDto));

        return "domain/experience/detail";
    }
}
