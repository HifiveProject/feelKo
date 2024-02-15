package com.ll.feelko.domain.member.api;

import com.ll.feelko.domain.member.api.Request.MemberRegisterRequest;
import com.ll.feelko.domain.member.application.MemberServiceImpl;
import com.ll.feelko.domain.member.dto.MemberProfileDto;
import com.ll.feelko.domain.member.dto.MemberRegisterDto;
import com.ll.feelko.domain.member.entity.Member;
import com.ll.feelko.global.security.SecurityUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String showRegister(Model model) {
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
                "complete",
                "FEELKO"
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
            Member member = memberService.findById(user.getId()).get();
            MemberProfileDto profileDto = new MemberProfileDto(
                    member.getEmail(),
                    member.getName(),
                    member.getProfile(),
                    member.getPhone(),
                    member.getStatus(),
                    member.getBirthday(),
                    member.getProvider()
            );
            if (member.getProvider().equals("KAKAO")) profileDto.setEmail(null);

            model.addAttribute("profileDto",profileDto);
            return "domain/member/mypage/profile-update";
        }

        return "global/historyBack";
    }

    // 이메일 중복 검사
    @GetMapping("/email/verification")
    @PreAuthorize("isAuthenticated() or isAnonymous()")
    @ResponseBody
    public ResponseEntity<?> verifyEmail(@RequestParam("email") String email,
                                         @RequestParam("provider") String provider,
                                         @RequestParam("status") String status){
        Map<String, Object> response = new HashMap<>();

        if(status.equals("incomplete") && memberService.emailIsExist(email) && List.of("KAKAO","NONE").contains(provider)){
            response.put("success", false);
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(response);
        }
        response.put("success", true);
        response.put("message", "사용 가능한 이메일입니다.");
        return ResponseEntity.ok(response);
    }
}
