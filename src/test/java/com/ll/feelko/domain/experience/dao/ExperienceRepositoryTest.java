package com.ll.feelko.domain.experience.dao;

import com.ll.feelko.domain.experience.entity.Experience;
import com.ll.feelko.domain.member.dto.UploadedPageDto;
import com.ll.feelko.domain.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
@DataJpaTest
class ExperienceRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Test
    void testFindIdTitleByMemberId() {
        // 테스트용 Member 엔티티 생성 및 저장
        Member member = Member.builder()
                .name("Test User")
                .email("test@example.com")
                .password("password")
                .profile("profileUrl")
                .phone("010-1234-5678")
                .birthday(null)
                .roles("ROLE_USER")
                .build();
        entityManager.persist(member);

        // 테스트용 Experience 엔티티 생성 및 저장
        for (int i = 0; i < 30; i++) {
            Experience experience = Experience.builder()
                    .member(member)
                    .title("Title " + i)
                    // Experience 엔티티의 다른 필요한 필드를 설정
                    .build();
            entityManager.persist(experience);
        }
        entityManager.flush(); //db에 저장되는 것과 같은 역할 (여기까지 given)

        // 페이징을 위한 Pageable 객체 생성
        Pageable pageable = PageRequest.of(0, 10); // 첫 페이지, 페이지 당 10개의 아이템

        // 테스트 실행
        Page<UploadedPageDto> result = experienceRepository.findIdTitleByMemberIdOrderByIdDesc(member.getId(), pageable);

        // 검증
        assertEquals(10, result.getContent().size()); // 반환된 페이지의 아이템 수 확인
        assertEquals(30, result.getTotalElements()); // 총 엔티티 수 확인
        assertEquals("Title 29", result.getContent().get(0).getTitle() );
    }
}