package com.ll.feelko.domain.wishlist.application;

import com.ll.feelko.domain.experience.dao.ExperienceRepository;
import com.ll.feelko.domain.experience.entity.Experience;
import com.ll.feelko.domain.member.dao.MemberRepository;
import com.ll.feelko.domain.member.entity.Member;
import com.ll.feelko.domain.wishlist.dao.WishListRepository;
import com.ll.feelko.domain.wishlist.dto.WishListDto;
import com.ll.feelko.domain.wishlist.entity.WishList;
import lombok.RequiredArgsConstructor;
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

    @Override
    @Transactional
    public boolean saveWish(WishListDto wishListDto) {
        Member member = memberRepository.findById(wishListDto.getMemberId()).get();
        Experience experience = experienceRepository.findById(wishListDto.getExperienceId()).get();

        Optional<WishList> optWish = wishListRepository.findByMemberIdAndExperienceId(wishListDto.getMemberId(), wishListDto.getExperienceId());

        if(optWish.isEmpty()) {
            WishList wishList = WishList.builder().
                    member(member).
                    experience(experience).
                    build();
            wishListRepository.save(wishList);
            return true;
        } else {
            wishListRepository.delete(optWish.get());
            return false;
        }
    }

}
