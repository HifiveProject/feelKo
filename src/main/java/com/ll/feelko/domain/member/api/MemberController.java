package com.ll.feelko.domain.member.api;

import com.ll.feelko.domain.member.application.MemberServiceImpl;
import com.ll.feelko.domain.member.dto.MemberRegisterDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ll.feelko.domain.member.api.Request.MemberRegisterRequest;

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
    public String register(@Valid MemberRegisterRequest registerRequest){
        //request를 dto로 변환
        MemberRegisterDto registerDto = new MemberRegisterDto(
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                registerRequest.getName(),
                registerRequest.getProfile(),
                registerRequest.getPhone(),
                registerRequest.getBirthday(),
                null
                );

        memberService.register(registerDto);
        return "redirect:/member/login";
    }

}
