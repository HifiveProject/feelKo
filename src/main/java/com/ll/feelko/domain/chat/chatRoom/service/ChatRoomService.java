package com.ll.feelko.domain.chat.chatRoom.service;

import com.ll.feelko.domain.chat.chatRoom.dto.ChatRoomListDto;
import com.ll.feelko.domain.chat.chatRoom.dto.ChatRoomMemberInfoDto;
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

import java.util.ArrayList;
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

    public List<ChatRoomListDto> findByMemberId(Long memberId) {
        List<Object[]> results = chatRoomRepository.findChatRoomsAndLatestMessageByMemberId(memberId);
        List<ChatRoomListDto> chatRooms = new ArrayList<>();
        for (Object[] result : results) {
            chatRooms.add(new ChatRoomListDto(
                    (Long) result[0], // chatRoomId
                    (String) result[1], // name
                    (Long) result[2], // latestMessageId
                    (String) result[3] // lastMessage
            ));
        }
        return chatRooms;
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
    public Long makeChatRoom(ChatRoomMemberInfoDto myInfoDto, ChatRoomMemberInfoDto theirInfoDto) {
        ChatRoom chatRoom = ChatRoom.builder().build();

        Long chatRoomId = chatRoomRepository.save(chatRoom).getId();
        //복합키 생성
        ChatRoomMemberId myRoomId = ChatRoomMemberId.builder()
                .chatRoomId(chatRoomId)
                .memberId(myInfoDto.getId())
                .build();

        ChatRoomMemberId theirRoomId = ChatRoomMemberId.builder()
                .chatRoomId(chatRoomId)
                .memberId(theirInfoDto.getId())
                .build();

        //중간 테이블 데이터 생성
        String chatRoomName = theirInfoDto.getName() + ", " + myInfoDto.getName();

        ChatRoomMember myChatRoomMember = ChatRoomMember.builder()
                .id(myRoomId)
                .member(memberRepository.findById(myInfoDto.getId())
                        .orElseThrow(() -> new RuntimeException("Member not found"))) // 멤버 엔티티 참조 설정
                .chatRoom(chatRoom)
                .chatRoomName(chatRoomName)
                .build();

        ChatRoomMember theirChatRoomMember = ChatRoomMember.builder()
                .id(theirRoomId)
                .member(memberRepository.findById(theirInfoDto.getId())
                        .orElseThrow(() -> new RuntimeException("Member not found"))) // 멤버 엔티티 참조 설정
                .chatRoom(chatRoom)
                .chatRoomName(chatRoomName)
                .build();

        chatRoomMemberRepository.save(myChatRoomMember);
        chatRoomMemberRepository.save(theirChatRoomMember);

        return chatRoomId;
    }

    //리팩토링 필요
    public ChatRoomMemberInfoDto createInfoDtoByExperienceId(Long experienceId) {
        Experience experience = experienceRepository.findById(experienceId).get();
        Member member = memberRepository.findById(experience.getMemberId()).get();

        return new ChatRoomMemberInfoDto(member.getId(), member.getName());
    }

    public ChatRoomMemberInfoDto createInfoDtoByEmail(String email) {
        Member member = memberRepository.findByEmail(email).get();

        return new ChatRoomMemberInfoDto(member.getId(), member.getName());
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

    public boolean isIncludeMe(long memberId, long roomId) {
        return chatRoomMemberRepository.existsByChatRoomIdAndMemberId(roomId, memberId);
    }

    public ChatRoomMember findChatRoomMemberByChatRoomIdAndMemberId(long memberId, long roomId){
        return chatRoomMemberRepository.findChatRoomMemberByChatRoomIdAndMemberId(memberId,roomId).orElseThrow();
    }

    @Transactional
    public boolean exitChatRoomByMemberIdAndChatRoomId(long memberId, long chatRoomId) {
        //삭제된 chatRoomMember의 개수를 리턴해서 0보다 크다면 삭제되었다고 판단
        Long successCode = chatRoomMemberRepository.deleteChatRoomMemberByChatRoomIdAndMemberId(chatRoomId, memberId);

        if (countMemberInChatRoom(chatRoomId) == 0) {
            chatRoomRepository.deleteById(chatRoomId);
            //채팅방에 남은 멤버가 없으면 채팅방을 제거
        }
        return successCode > 0;
    }

    public Long countMemberInChatRoom(long chatRoomId) {
        return chatRoomMemberRepository.countByChatRoomId(chatRoomId);
    }

    @Transactional
    public void modifyChatRoomName(long memberId, long chatRoomId, String chatRoomName) {
        ChatRoomMember chatRoomMember = chatRoomMemberRepository.findChatRoomMemberByChatRoomIdAndMemberId(chatRoomId, memberId).orElseThrow();
        chatRoomMember.setChatRoomName(chatRoomName);
        chatRoomMemberRepository.save(chatRoomMember);
    }
}
