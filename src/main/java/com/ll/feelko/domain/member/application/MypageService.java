package com.ll.feelko.domain.member.application;

import com.ll.feelko.domain.member.entity.Member;

import java.util.Optional;

public interface MypageService {
    Optional<Member> findById(Long id);
}
