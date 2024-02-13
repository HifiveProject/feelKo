package com.ll.feelko.domain.chat.chatRoom.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.feelko.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ChatRoomMember {
    @EmbeddedId
    private ChatRoomMemberId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("chatRoomId") // ChatMemberId 내 chatRoomId 매핑
    @JoinColumn(name = "chat_room_id")
    @JsonIgnore // 무한 재귀 방지
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("memberId") // ChatMemberId 내 memberId 매핑
    @JoinColumn(name = "member_id")
    private Member member;

    private String chatRoomName; //사용자마다 채팅방 이름을 다르게 설정할 수 있도록

    private String imageUrl;

    public void setChatRoomName(String chatRoomName){
        this.chatRoomName = chatRoomName;
    }
}
