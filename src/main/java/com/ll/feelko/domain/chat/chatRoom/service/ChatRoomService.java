package com.ll.feelko.domain.chat.chatRoom.service;

import com.ll.feelko.domain.chat.chatMessage.entity.ChatMessage;
import com.ll.feelko.domain.chat.chatRoom.dto.TheirInfoDto;
import com.ll.feelko.domain.chat.chatRoom.entity.ChatRoom;
import com.ll.feelko.domain.chat.chatRoom.entity.ChatRoomMember;
import com.ll.feelko.domain.chat.chatRoom.entity.ChatRoomMemberId;
import com.ll.feelko.domain.chat.chatRoom.repository.ChatRoomMemberRepository;
import com.ll.feelko.domain.chat.chatRoom.repository.ChatRoomRepository;
import com.ll.feelko.domain.experience.dao.ExperienceRepository;
import com.ll.feelko.domain.experience.entity.Experience;
import com.ll.feelko.domain.member.dao.MemberRepository;
import com.ll.feelko.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final MemberRepository memberRepository;
    private final ExperienceRepository experienceRepository;

    @Transactional
    public ChatRoom make(String name) {
        ChatRoom chatRoom = ChatRoom.builder()
                .name(name)
                .build();

        chatRoomRepository.save(chatRoom);

        return chatRoom;
    }

    public List<ChatRoom> findByMemberId(Long memberId) {
        return chatRoomMemberRepository.findChatRoomListByMemberId(memberId);
    }

    @Transactional
    public ChatMessage write(long roomId, String writerName, String content) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).get();

        ChatMessage chatMessage = chatRoom.writeMessage(writerName, content);

        return chatMessage;
    }

    public Long findChatRoom(Long myId, Long theirId) {
        HashSet<Long> myRoomIdSet = chatRoomMemberRepository.findAllByMemberIdAsSet(myId);
        List<Long> theirRoomIdList = chatRoomMemberRepository.findAllByMemberIdAsList(theirId);

        //상대와 중복되는 채팅방이 있는지
        for (Long theirRoomId : theirRoomIdList) {
            if (myRoomIdSet.contains(theirRoomId)) return theirRoomId;
        }
        return -1L; //채팅방이 없음
    }

    @Transactional
    public Long makeChatRoom(Long myId, TheirInfoDto theirInfoDto) {
        //상대 이름으로 방 이름 설정
        ChatRoom chatRoom = ChatRoom.builder().name(theirInfoDto.getName()).build();

        Long chatRoomId = chatRoomRepository.save(chatRoom).getId();
        //복합키 생성
        ChatRoomMemberId myRoomId = ChatRoomMemberId.builder()
                .chatRoomId(chatRoomId)
                .memberId(myId)
                .build();

        ChatRoomMemberId theirRoomId = ChatRoomMemberId.builder()
                .chatRoomId(chatRoomId)
                .memberId(theirInfoDto.getId())
                .build();
        //중간 테이블 데이터 생성
        ChatRoomMember myChatRoomMember = ChatRoomMember.builder()
                .id(myRoomId)
                .member(memberRepository.findById(myId).orElseThrow(() -> new RuntimeException("Member not found"))) // 멤버 엔티티 참조 설정
                .chatRoom(chatRoom)
                .build();

        ChatRoomMember theirChatRoomMember = ChatRoomMember.builder()
                .id(theirRoomId)
                .member(memberRepository.findById(theirInfoDto.getId()).orElseThrow(() -> new RuntimeException("Member not found"))) // 멤버 엔티티 참조 설정
                .chatRoom(chatRoom)
                .build();

        chatRoomMemberRepository.save(myChatRoomMember);
        chatRoomMemberRepository.save(theirChatRoomMember);

        return chatRoomId;
    }

    //리팩토링 필요
    public TheirInfoDto createInfoDtoByExperienceId(Long experienceId){
        Experience experience = experienceRepository.findById(experienceId).get();
        Member member = memberRepository.findById(experience.getMemberId()).get();

        return new TheirInfoDto(member.getId(),member.getName());
    }

    //리팩토링 필요
    public TheirInfoDto createInfoDtoByEmail(String email){
        Member member = memberRepository.findByEmail(email).get();

        return new TheirInfoDto(member.getId(),member.getName());
    }

    public Optional<ChatRoom> findById(long roomId) {
        return chatRoomRepository.findById(roomId);
    }

    public boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
