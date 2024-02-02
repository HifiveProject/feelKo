package com.ll.feelko.domain.member.application;

import com.ll.feelko.domain.member.dto.MemberProfileDto;
import com.ll.feelko.domain.member.dto.MemberProfileUpdateDto;
import com.ll.feelko.domain.member.dto.UploadReservationDto;
import com.ll.feelko.domain.member.dto.UploadedPageDto;
import com.ll.feelko.domain.member.entity.Member;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

public interface MypageService {
    Optional<Member> findById(Long id);
    void updateProfile(Long id, MemberProfileUpdateDto profileUpdateDto);

    Page<UploadedPageDto> getUploadedPageList(long memberId, int page, int size);

    MemberProfileDto getProfile(long id);

    boolean isMyUploadedPage(long id, Long experienceId);

    TreeMap<LocalDate, List<UploadReservationDto>> getUploadedPageReservation(Long experienceId);
}
