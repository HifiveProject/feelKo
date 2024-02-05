package com.ll.feelko.domain.wishlist.application;

import com.ll.feelko.domain.experience.dao.ExperienceRepository;
import com.ll.feelko.domain.experience.entity.Experience;
import com.ll.feelko.domain.member.dao.MemberRepository;
import com.ll.feelko.domain.member.entity.Member;
import com.ll.feelko.domain.wishlist.dao.WishListRepository;
import com.ll.feelko.domain.wishlist.dto.WishListDto;
import com.ll.feelko.domain.wishlist.dto.WishListPageDto;
import com.ll.feelko.domain.wishlist.entity.WishList;
import com.ll.feelko.global.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishListServiceImpl implements WishListService {
    private final WishListRepository wishListRepository;
    private final MemberRepository memberRepository;
    private final ExperienceRepository experienceRepository;

    // 찜 여부 확인
    @Override
    public boolean checkWish(WishListDto wishListDto) {
        return wishListRepository.existsByMemberIdAndExperienceId(wishListDto.getMemberId(), wishListDto.getExperienceId());
    }

    // 찜 등록 및 해제
    @Override
    @Transactional
    public boolean saveWish(WishListDto wishListDto) {
        Member member = memberRepository.findById(wishListDto.getMemberId()).get();
        Experience experience = experienceRepository.findById(wishListDto.getExperienceId()).get();

        if (!checkWish(wishListDto)) {
            WishList wishList = WishList.builder().
                    member(member).
                    experience(experience).
                    build();
            wishListRepository.save(wishList);
            experience.increaseCount();
            return true;
        } else {
            WishList wishListToDelete = wishListRepository.findByMemberIdAndExperienceId(wishListDto.getMemberId(), wishListDto.getExperienceId()).get();
            wishListRepository.delete(wishListToDelete);
            experience.decreaseCount();
            return false;
        }
    }

    @Override
    public Page<WishListPageDto> getWishList(long memberId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return wishListRepository.findIdTitleByMemberIdOrderByIdDesc(memberId, pageable);
    }

    @Override
    public Optional<WishListDto> createWishListDtoIfLogined(SecurityUser user, Long experienceId) {
        if (user != null) {
            return Optional.of(new WishListDto(user.getId(), experienceId));
        } else {
            return Optional.empty();
        }
    }

}
