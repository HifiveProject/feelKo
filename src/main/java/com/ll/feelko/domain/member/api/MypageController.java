package com.ll.feelko.domain.member.api;

import com.ll.feelko.domain.member.application.MypageServiceImpl;
import com.ll.feelko.domain.member.entity.Member;
import com.ll.feelko.global.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member/mypage")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class MypageController {

    private final MypageServiceImpl mypageService;

    @GetMapping("/profile")
    public String showProfile(@AuthenticationPrincipal SecurityUser user, Model model){
        Member member = mypageService.findById(user.getId()).get();
        model.addAttribute("member", member);
        return "domain/member/profile";
    }

}
