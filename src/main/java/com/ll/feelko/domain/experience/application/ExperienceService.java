package com.ll.feelko.domain.experience.application;

import com.ll.feelko.domain.experience.dao.ExperienceRepository;
import com.ll.feelko.domain.experience.dto.ExperienceCreateDTO;
import com.ll.feelko.domain.experience.entity.Experience;
import com.ll.feelko.domain.member.application.MemberService;
import com.ll.feelko.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExperienceService {

    private final MemberService memberService;
    private final ExperienceRepository experienceRepository;

    public Experience createExperience(ExperienceCreateDTO dto) {

        Member member = memberService.findByIdElseThrow(dto.getMemberId());

        Experience experience = Experience.builder()
                .member(member)
                .title(dto.getTitle())
                .location(dto.getLocation())
                // TODO 추후 삭제 요망
                .headcount(10L)
                // TODO 실제 이미지처리가 필요함
                .imageUrl("https://www.localnaeil.com/FileData/Article/201705/4e808dfd-795e-4671-b596-7e64e22d868a.jpg")
                .build();
        return experienceRepository.save(experience);
    }

    public Experience detail(Long experienceId) {
        Experience experience = findByIdElseThrow(experienceId);

        return experience;
    }

    public Experience findByIdElseThrow(Long experienceId) {
        return experienceRepository.findById(experienceId)
                .orElseThrow(() -> new RuntimeException("체험을 찾을 수 없습니다."));
    }
}
