package com.ll.feelko.domain.chat.chatMessage.api.response;

import com.ll.feelko.domain.chat.chatMessage.entity.ChatMessage;
import lombok.Getter;

@Getter
public class WriteResponseBody {
    private ChatMessage message;
    private String chatRoomName;
    private String eventType;
    private String imageUrl;

    public WriteResponseBody(ChatMessage chatMessage,String eventType){
        this.message = chatMessage;
        this.eventType = eventType;
    }

    public void setChatRoomName(String chatRoomName){
        this.chatRoomName = chatRoomName;
    }
    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }
}
