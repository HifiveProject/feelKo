package com.ll.feelko.domain.member.application;

import com.ll.feelko.domain.experience.dao.ExperienceRepository;
import com.ll.feelko.domain.experience.entity.Experience;
import com.ll.feelko.domain.member.dao.MemberRepository;
import com.ll.feelko.domain.member.dto.*;
import com.ll.feelko.domain.member.entity.Member;
import com.ll.feelko.domain.payment.dao.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MypageServiceImpl implements MypageService {

    private final ExperienceRepository experienceRepository;
    private final MemberRepository memberRepository;
    private final PaymentRepository paymentRepository;

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
    public MemberProfileDto getProfile(long id) {
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
    public boolean isMyUploadedPage(long id, Long experienceId) {
        Optional<Experience> optExp = experienceRepository.findById(experienceId);
        if (optExp.isEmpty()) throw new RuntimeException("체험을 찾을 수 없습니다.");
        return id == optExp.get().getMemberId();
    }

    @Override
    public TreeMap<LocalDate, List<UploadReservationDto>> getUploadedPageReservation(Long experienceId) {
        List<UploadReservationDto> reservations = paymentRepository.findByExperienceIdWithMemberInfo(experienceId);

        return reservations.stream()
                .collect(Collectors.groupingBy(
                        UploadReservationDto::getReservationDate,
                        () -> new TreeMap<>(Comparator.reverseOrder()), // key 내림차순 정렬
                        Collectors.toList()
                ));

    }

    @Override
    public Page<ReservationDto> getReservationListByMemberId(long memberId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return paymentRepository.findByMemberIdOrderByCreatedAtDescWithExperience(memberId, pageable);
    }

}
