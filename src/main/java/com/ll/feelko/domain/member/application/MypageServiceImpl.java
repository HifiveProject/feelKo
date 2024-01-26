package com.ll.feelko.domain.member.application;

import com.ll.feelko.domain.experience.dao.ExperienceRepository;
import com.ll.feelko.domain.member.dto.uploadedPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MypageServiceImpl implements MypageService {

    private final ExperienceRepository experienceRepository;

    @Override
    public Page<uploadedPageDto> getUploadedPageList(long memberId, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return experienceRepository.findIdTitleByMemberIdOrderByIdDesc(memberId, pageable);
    }
}
