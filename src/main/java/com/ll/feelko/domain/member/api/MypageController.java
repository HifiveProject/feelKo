package com.ll.feelko.domain.member.api;

import com.ll.feelko.domain.member.application.MypageService;
import com.ll.feelko.domain.member.dto.uploadePageDto;
import com.ll.feelko.global.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/member/mypage")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class MypageController {

    private final MypageService mypageService;

    @GetMapping("/upload-list")
    public String showUploadedPageList(@AuthenticationPrincipal SecurityUser user,
                                       @RequestParam(name = "page", defaultValue = "0") int page,
                                       @RequestParam(name = "size", defaultValue = "10") int size,
                                       Model model) {
        Page<uploadePageDto> uploads = mypageService.getUploadedPageList(user.getId(), page, size);
        model.addAttribute(uploads);
        return "domain/member/mypage/uploadList";
    }

}
