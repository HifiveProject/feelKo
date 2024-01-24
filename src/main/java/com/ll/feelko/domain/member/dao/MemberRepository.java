package com.ll.feelko.domain.member.dao;

import com.ll.feelko.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findById(Long id);
    Optional<Member> findByEmail(String email);
}
