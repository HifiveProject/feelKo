package com.ll.feelko.domain.chat.chatMessage.api.response;

import com.ll.feelko.domain.chat.chatMessage.entity.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WriteResponseBody {
    private ChatMessage message;
}
