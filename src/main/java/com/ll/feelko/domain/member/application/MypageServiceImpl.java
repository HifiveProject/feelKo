package com.ll.feelko.domain.member.application;

import com.ll.feelko.domain.experience.dao.ExperienceRepository;
import com.ll.feelko.domain.experience.entity.Experience;
import com.ll.feelko.domain.member.dao.MemberRepository;
import com.ll.feelko.domain.member.dto.MemberProfileDto;
import com.ll.feelko.domain.member.dto.MemberProfileUpdateDto;
import com.ll.feelko.domain.member.dto.UploadReservationDto;
import com.ll.feelko.domain.member.dto.UploadedPageDto;
import com.ll.feelko.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MypageServiceImpl implements MypageService {

    private final ExperienceRepository experienceRepository;
    private final MemberRepository memberRepository;

    @Override
    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public Page<UploadedPageDto> getUploadedPageList(long memberId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return experienceRepository.findIdTitleByMemberIdOrderByIdDesc(memberId, pageable);
    }

    @Override
    public MemberProfileDto getProfile(long id){
        Optional<Member> optMember = memberRepository.findById(id);

        Member member = optMember.get();
        return new MemberProfileDto(
                member.getEmail(),
                member.getName(),
                member.getProfile(),
                member.getPhone(),
                member.getBirthday()
        );

    }

    @Override
    @Transactional
    public void updateProfile(Long id, MemberProfileUpdateDto profileUpdateDto) {
        Member member = memberRepository.findById(id).get();
        member.updateProfile(profileUpdateDto.getProfile());
    }

    @Override
    public boolean isMyUploadedPage(long id, Long experienceId){
        Optional<Experience> optExp = experienceRepository.findById(experienceId);
        if(optExp.isEmpty()) throw new RuntimeException("체험을 찾을 수 없습니다.");
        return id==optExp.get().getMember().getId();
    }

    @Override
    public Map<LocalDate, UploadReservationDto> getUploadedPageReservation(Long experienceId){
        PaymentRepository
    }
}