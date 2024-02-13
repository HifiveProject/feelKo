package com.ll.feelko.domain.chat.chatRoom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChatRoomListDto {
    private Long chatRoomId;
    private String name;
    private Long latestMessageId;
    private String lastMessage;
    private String lastWriter;
    private String imageUrl;
}
