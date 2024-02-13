package com.ll.feelko.domain.chat.chatRoom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChatRoomMemberInfoDto {
    Long id;
    String name;
    String imageUrl;
}
