package com.ll.feelko.domain.chat.chatMessage.service;

import com.ll.feelko.domain.chat.chatMessage.api.response.WriteResponseBody;
import com.ll.feelko.domain.chat.chatMessage.entity.ChatMessage;
import com.ll.feelko.domain.chat.chatMessage.repository.ChatMessageRepository;
import com.ll.feelko.domain.chat.chatRoom.entity.ChatRoom;
import com.ll.feelko.domain.chat.chatRoom.entity.ChatRoomMember;
import com.ll.feelko.domain.chat.chatRoom.repository.ChatRoomMemberRepository;
import com.ll.feelko.domain.chat.chatRoom.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public ChatMessage writeAndSend(long roomId, String writerName, String content, String eventType, long senderId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).get();

        ChatMessage chatMessage = writeMessage(chatRoom, writerName, content, senderId);
        sendMessageToRoomAndList(chatRoom, new WriteResponseBody(chatMessage, eventType));

        return chatMessage;
    }

    @Transactional
    public ChatMessage writeMessage(ChatRoom chatRoom, String writerName, String content, long senderId) {
        ChatMessage chatMessage = ChatMessage
                .builder()
                .chatRoom(chatRoom)
                .writerName(writerName)
                .content(content)
                .senderId(senderId)
                .build();

        chatMessage = chatMessageRepository.save(chatMessage);

        return chatMessage;
    }

    public void sendMessageToRoomAndList(ChatRoom chatRoom, WriteResponseBody writeBody){
        //채팅방에 메세지 보내기
        messagingTemplate.convertAndSend("/topic/chat/room/" + chatRoom.getId() + "/message", writeBody);

        List<ChatRoomMember> chatRoomMembers = chatRoomMemberRepository.findChatRoomMemberByChatRoomId(chatRoom.getId());
        //채팅 방에 있는 모든 멤버의 list에 메세지 보내기
        for(ChatRoomMember chatRoomMember : chatRoomMembers){
            writeBody.setChatRoomName(chatRoomMember.getChatRoomName());
            messagingTemplate.convertAndSend("/topic/chat/room/list/"
                                             + chatRoomMember.getId().getMemberId() //memberId
                                             + "/message", writeBody);
        }

    }



    public List<ChatMessage> findByChatRoomIdAndIdAfter(long roomId, long afterId) {
        return chatMessageRepository.findByChatRoomIdAndIdAfter(roomId, afterId);
    }

}
