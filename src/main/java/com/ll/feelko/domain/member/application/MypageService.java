package com.ll.feelko.domain.member.application;

import com.ll.feelko.domain.member.dto.uploadedPageDto;
import org.springframework.data.domain.Page;

public interface MypageService {
    Page<uploadedPageDto> getUploadedPageList(long memberId, int page, int size);
}
