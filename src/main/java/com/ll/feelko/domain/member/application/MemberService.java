package com.ll.feelko.domain.member.application;



import com.ll.feelko.domain.member.dto.MemberRegisterDto;
import com.ll.feelko.domain.member.entity.Member;

import java.util.Optional;

public interface MemberService {
    Member register(MemberRegisterDto registerDto);
    Optional<Member> findByEmail(String email);

    boolean emailIsExist(String email);

    long getMemberCount();
    Member findByIdElseThrow(Long memberId);
}
