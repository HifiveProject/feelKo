package com.ll.feelko.domain.member.api;

import com.ll.feelko.domain.member.api.Request.MemberRegisterRequest;
import com.ll.feelko.domain.member.application.MemberServiceImpl;
import com.ll.feelko.domain.member.dto.MemberProfileDto;
import com.ll.feelko.domain.member.dto.MemberRegisterDto;
import com.ll.feelko.global.security.SecurityUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@PreAuthorize("isAnonymous()")
public class MemberController {

    private final MemberServiceImpl memberService;

    @GetMapping("/login")
    public String showLogin() {
        return "domain/member/login";
    }

    @GetMapping("/register")
    public String showRegister() {
        return "domain/member/register";
    }

    @PostMapping("/register")
    public String register(@Valid MemberRegisterRequest registerRequest) {
        //request를 dto로 변환
        MemberRegisterDto registerDto = new MemberRegisterDto(
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                registerRequest.getName(),
                registerRequest.getProfile(),
                registerRequest.getPhone(),
                registerRequest.getBirthday(),
                null,
                "complete"
        );

        memberService.register(registerDto);
        return "redirect:/member/login";
    }

    @GetMapping("/auth-result")
    @PreAuthorize("isAuthenticated() or isAnonymous()")
    public String redirectBack(
            HttpServletRequest request,
            @RequestParam(required = false) String msg,
            @RequestParam(required = false) String failMsg) {

        if (msg != null) request.setAttribute("msg", msg);
        if (failMsg != null) request.setAttribute("failMsg", failMsg);

        return "global/historyBack";
    }

    @GetMapping("/oauth2-result")
    @PreAuthorize("isAuthenticated()")
    public String redirectBack2(
            HttpServletRequest request,
            @RequestParam(required = false) String msg,
            @RequestParam(required = false) String failMsg,
            @AuthenticationPrincipal SecurityUser user,
            Model model) {
        if (msg != null) request.setAttribute("msg", msg);
        if (failMsg != null) request.setAttribute("failMsg", failMsg);

        if(user.getStatus().equals("incomplete")) {
            MemberProfileDto profileDto = new MemberProfileDto(
                    user.getUsername(),
                    user.getName(),
                    user.getProfileImage(),
                    null,
                    user.getStatus(),
                    null
            );
            model.addAttribute("profileDto",profileDto);
            return "domain/member/mypage/profile-update";
        }

        return "global/historyBack";
    }
}
