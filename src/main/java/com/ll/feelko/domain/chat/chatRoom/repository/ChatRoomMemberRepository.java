package com.ll.feelko.domain.chat.chatRoom.repository;

import com.ll.feelko.domain.chat.chatRoom.entity.ChatRoom;
import com.ll.feelko.domain.chat.chatRoom.entity.ChatRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.HashSet;
import java.util.List;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember,Long> {
    List<ChatRoom> findByMemberId(Long memberId);

    @Query("SELECT e.id.chatRoomId FROM ChatRoomMember e WHERE e.id.memberId = :memberId")
    HashSet<Long> findAllByMemberIdAsSet(Long memberId);

    @Query("SELECT e.id.chatRoomId FROM ChatRoomMember e WHERE e.id.memberId = :memberId")
    List<Long> findAllByMemberIdAsList(Long memberId);

    // MemberId로 ChatRoom 목록 조회
//    @Query("SELECT crm.chatRoom FROM ChatRoomMember crm WHERE crm.member.id = :memberId")
//    List<ChatRoom> findChatRoomListByMemberId(Long memberId);

    boolean existsByChatRoomIdAndMemberId(long roomId, long id);


}
