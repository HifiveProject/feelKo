package com.ll.feelko.domain.member.application;

import com.ll.feelko.domain.member.dto.uploadePageDto;
import org.springframework.data.domain.Page;

public interface MypageService {
    Page<uploadePageDto> getUploadedPageList(long memberId, int page, int size);
}
