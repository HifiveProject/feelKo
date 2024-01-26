package com.ll.feelko.domain.member.application;

import com.ll.feelko.domain.member.dto.MemberProfileUpdateDto;
import com.ll.feelko.domain.member.entity.Member;
import com.ll.feelko.domain.member.dto.uploadedPageDto;
import org.springframework.data.domain.Page;
import java.util.Optional;

public interface MypageService {
    Optional<Member> findById(Long id);
    void updateProfile(Long id, MemberProfileUpdateDto updateDto);

    Page<uploadedPageDto> getUploadedPageList(long memberId, int page, int size);

    MemberProfileUpdateDto getProfile(long id);
}
