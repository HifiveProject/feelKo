package com.ll.feelko.domain.member.application;

import com.ll.feelko.domain.member.dao.MemberRepository;
import com.ll.feelko.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MypageServiceImpl implements MypageService{
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<Member> findById(Long id){
        return memberRepository.findById(id);
    }
}
