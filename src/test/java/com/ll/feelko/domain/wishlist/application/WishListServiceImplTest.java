package com.ll.feelko.domain.wishlist.application;

import com.ll.feelko.domain.experience.dao.ExperienceRepository;
import com.ll.feelko.domain.experience.entity.Experience;
import com.ll.feelko.domain.member.dao.MemberRepository;
import com.ll.feelko.domain.member.entity.Member;
import com.ll.feelko.domain.wishlist.dao.WishListRepository;
import com.ll.feelko.domain.wishlist.dto.WishListSaveDto;
import com.ll.feelko.domain.wishlist.entity.WishList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class WishListServiceImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ExperienceRepository experienceRepository;

    private WishListServiceImpl wishListService;

    @BeforeEach
    public void setUp() {
        wishListService = new WishListServiceImpl(wishListRepository, memberRepository, experienceRepository);
    }

    @Test
    @DisplayName("회원이 체험을 찜 목록에 추가하는 테스트")
    public void saveWish() {
        // given
        Member member = Mockito.mock(Member.class);
        Experience experience = Mockito.mock(Experience.class);

        ReflectionTestUtils.setField(member, "id", 1L);
        ReflectionTestUtils.setField(experience, "id", 1L);
        ReflectionTestUtils.setField(experience, "wishCounter", 0L);

        member = entityManager.merge(member);
        experience = entityManager.merge(experience);

        WishListSaveDto wishListSaveDto = new WishListSaveDto(member.getId(), experience.getId());

        // when
        wishListService.saveWish(wishListSaveDto);

        // then
        List<WishList> wishLists = wishListRepository.findAll();
        assertThat(wishLists).hasSize(1);
        assertThat(wishLists.get(0).getMember().getId()).isEqualTo(member.getId());
        assertThat(wishLists.get(0).getExperience().getId()).isEqualTo(experience.getId());

        assertThat(ReflectionTestUtils.getField(experience, "wishCounter")).isEqualTo(1L);
    }
}