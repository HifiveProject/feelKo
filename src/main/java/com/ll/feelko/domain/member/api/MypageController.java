package com.ll.feelko.domain.member.api;

import com.ll.feelko.domain.member.api.Request.MemberProfileUpdateRequest;
import com.ll.feelko.domain.member.application.MypageService;
import com.ll.feelko.domain.member.dto.*;
import com.ll.feelko.global.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;

@Controller
@RequestMapping("/member/mypage")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class MypageController {

    private final MypageService mypageService;

    // 개인정보 열람
    @GetMapping("")
    public String showMypage(@AuthenticationPrincipal SecurityUser user, Model model){
        MemberProfileDto profileDto = mypageService.getProfile(user.getId());
        model.addAttribute("profileDto", profileDto);
        return "domain/member/mypage";
    }

    // 개인정보 수정
    @GetMapping("/profile/update")
    public String showUpdateProfile(@AuthenticationPrincipal SecurityUser user, Model model){
        MemberProfileDto profileDto = mypageService.getProfile(user.getId());
        model.addAttribute("profileDto", profileDto);
        return "domain/member/mypage/profile-update";
    }

    @PostMapping("/profile/update")
    public String updateProfile(MemberProfileUpdateRequest profileUpdateRequest, @AuthenticationPrincipal SecurityUser user){
        MemberProfileUpdateDto profileUpdateDto = new MemberProfileUpdateDto(
                profileUpdateRequest.getProfile()
        );
        mypageService.updateProfile(user.getId(), profileUpdateDto);
        return "redirect:/member/mypage";
    }

    @GetMapping("/upload-list")
    public String showUploadedPageList(@AuthenticationPrincipal SecurityUser user,
                                       @RequestParam(name = "page", defaultValue = "1") int page,
                                       @RequestParam(name = "size", defaultValue = "9") int size,
                                       Model model) {
        Page<UploadedPageDto> uploads = mypageService.getUploadedPageList(user.getId(), page-1, size);
        model.addAttribute("uploads",uploads);
        return "domain/member/mypage/uploadList";
    }

    @GetMapping("/upload-list/reservation/{experienceId}")
    @ResponseBody
    public ResponseEntity<?> showUploadedPageReservation(@AuthenticationPrincipal SecurityUser user,
                                                        @PathVariable Long experienceId) {
        TreeMap<LocalDate, List<UploadReservationDto>> reservations = null; //예약날짜별로 구분된 예약

        if(!mypageService.isMyUploadedPage(user.getId(),experienceId)){
           return ResponseEntity
                   .badRequest()
                   .body("페이지에 접근할 권한이 없습니다.");
        }
        reservations = mypageService.getUploadedPageReservation(experienceId);

        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/reservation-list")
    public String showReservationList(@AuthenticationPrincipal SecurityUser user,
                                      @RequestParam(name = "page", defaultValue = "1") int page,
                                      @RequestParam(name = "size", defaultValue = "9") int size,
                                      Model model) {
        Page<ReservationDto> reservations = mypageService.getReservationListByMemberId(user.getId(), page-1, size);
        model.addAttribute("reservations",reservations);
        return "domain/member/mypage/reservation-list";
    }

}
