package com.ll.feelko.domain.member.application;

import com.ll.feelko.domain.member.dto.MemberProfileUpdateDto;
import com.ll.feelko.domain.member.entity.Member;

import java.util.Optional;

public interface MypageService {
    Optional<Member> findById(Long id);
    void updateProfile(Long id, MemberProfileUpdateDto updateDto);
}
