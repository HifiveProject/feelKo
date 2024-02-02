package com.ll.feelko.domain.wishlist.application;

import com.ll.feelko.domain.experience.dao.ExperienceRepository;
import com.ll.feelko.domain.experience.entity.Experience;
import com.ll.feelko.domain.member.dao.MemberRepository;
import com.ll.feelko.domain.member.entity.Member;
import com.ll.feelko.domain.wishlist.dao.WishListRepository;
import com.ll.feelko.domain.wishlist.dto.WishListDto;
import com.ll.feelko.domain.wishlist.dto.WishListSaveDto;
import com.ll.feelko.domain.wishlist.entity.WishList;
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
public class WishListServiceImpl implements WishListService{
    private final WishListRepository wishListRepository;
    private final MemberRepository memberRepository;
    private final ExperienceRepository experienceRepository;

    // 찜 목록 등록 및 해제
    @Override
    @Transactional
    public boolean saveWish(WishListSaveDto wishListSaveDto) {
        Member member = memberRepository.findById(wishListSaveDto.getMemberId()).get();
        Experience experience = experienceRepository.findById(wishListSaveDto.getExperienceId()).get();

        Optional<WishList> optWish = wishListRepository.findByMemberIdAndExperienceId(wishListSaveDto.getMemberId(), wishListSaveDto.getExperienceId());

        if(optWish.isEmpty()) {
            WishList wishList = WishList.builder().
                    member(member).
                    experience(experience).
                    build();
            wishListRepository.save(wishList);
            experience.increaseCount();
            return true;
        } else {
            wishListRepository.delete(optWish.get());
            experience.decreaseCount();
            return false;
        }
    }

    @Override
    public Page<WishListDto> getWishList(long memberId, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return wishListRepository.findIdTitleByMemberIdOrderByIdDesc(memberId, pageable);
    }

}
