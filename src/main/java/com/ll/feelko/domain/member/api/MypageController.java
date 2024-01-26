package com.ll.feelko.domain.member.api;

import com.ll.feelko.domain.member.api.Request.MemberProfileUpdateRequest;
import com.ll.feelko.domain.member.application.MypageServiceImpl;
import com.ll.feelko.domain.member.dto.MemberProfileUpdateDto;
import com.ll.feelko.domain.member.entity.Member;
import com.ll.feelko.global.security.SecurityUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member/mypage")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class MypageController {

    private final MypageServiceImpl mypageService;

    // 개인정보 열람
    @GetMapping("/profile")
    public String showProfile(@AuthenticationPrincipal SecurityUser user, Model model){
        Member member = mypageService.findById(user.getId()).get();
        model.addAttribute("member", member);
        return "domain/member/mypage/profile";
    }

    // 개인정보 수정
    @GetMapping("/profile/update")
    public String showUpdateProfile(@AuthenticationPrincipal SecurityUser user, Model model){
        Member member = mypageService.findById(user.getId()).get();
        model.addAttribute("member", member);
        return "domain/member/mypage/profile-update";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@Valid MemberProfileUpdateRequest profileUpdateRequest, @AuthenticationPrincipal SecurityUser user){
        // request를 dto로 변환
        MemberProfileUpdateDto updateDto = new MemberProfileUpdateDto(
                profileUpdateRequest.getPassword(),
                profileUpdateRequest.getName(),
                profileUpdateRequest.getProfile(),
                profileUpdateRequest.getPhone(),
                profileUpdateRequest.getBirthday()
        );
        // 현재 로그인된 사용자의 id를 가져옵니다.
        Long id = user.getId();

        mypageService.updateProfile(id, updateDto);
        return "redirect:/member/mypage/profile";
    }




}
