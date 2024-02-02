package com.ll.feelko.domain.main.application;

import com.ll.feelko.domain.member.application.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MainServiceImpl implements MainService{

    private final MemberService memberService;

    // MainServiceImpl에서 MemberService를 주입
    public MainServiceImpl(MemberService memberService) {
        this.memberService = memberService;
    }


}
