package com.ll.feelko.domain.member.application;



import com.ll.feelko.domain.member.dto.MemberRegisterDto;
import com.ll.feelko.domain.member.entity.Member;

import java.util.Optional;

public interface MemberService {
    public Member register(MemberRegisterDto registerDto);
    Optional<Member> findByEmail(String email);
}
