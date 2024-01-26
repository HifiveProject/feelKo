package com.ll.feelko.domain.member.api;

import com.ll.feelko.domain.member.api.Request.MemberProfileUpdateRequest;
import com.ll.feelko.domain.member.application.MypageService;
import com.ll.feelko.domain.member.dto.MemberProfileUpdateDto;
import com.ll.feelko.domain.member.dto.uploadedPageDto;
import com.ll.feelko.domain.member.entity.Member;
import com.ll.feelko.global.security.SecurityUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/member/mypage")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class MypageController {

    private final MypageService mypageService;

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

    @GetMapping("/upload-list")
    public String showUploadedPageList(@AuthenticationPrincipal SecurityUser user,
                                       @RequestParam(name = "page", defaultValue = "0") int page,
                                       @RequestParam(name = "size", defaultValue = "10") int size,
                                       Model model) {
        Page<uploadedPageDto> uploads = mypageService.getUploadedPageList(user.getId(), page, size);
        model.addAttribute("uploads",uploads);
        return "domain/member/mypage/uploadList";
    }

}
