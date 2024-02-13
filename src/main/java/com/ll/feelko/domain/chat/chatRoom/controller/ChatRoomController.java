package com.ll.feelko.domain.chat.chatRoom.controller;

import com.ll.feelko.domain.chat.chatMessage.entity.ChatMessage;
import com.ll.feelko.domain.chat.chatMessage.service.ChatMessageService;
import com.ll.feelko.domain.chat.chatRoom.dto.ChatRoomListDto;
import com.ll.feelko.domain.chat.chatRoom.dto.ChatRoomMemberInfoDto;
import com.ll.feelko.domain.chat.chatRoom.entity.ChatRoom;
import com.ll.feelko.domain.chat.chatRoom.service.ChatRoomService;
import com.ll.feelko.global.rsData.RsData;
import com.ll.feelko.global.security.SecurityUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/chat/room")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("/{roomId}")
    public String showRoom(
            @PathVariable final long roomId,
            @AuthenticationPrincipal SecurityUser user,
            Model model
    ) {
        if(!chatRoomService.isIncludeMe(user.getId(),roomId)){
            throw new RuntimeException("참여 권한이 없습니다.");
        }

        ChatRoom room = chatRoomService.findById(roomId).get();

        model.addAttribute("room", room);

        return "domain/chat/chatRoom/room";
    }

    @GetMapping("/list")
    public String showList(
            @AuthenticationPrincipal SecurityUser user,
            Model model) {
        List<ChatRoomListDto> chatRooms = chatRoomService.findByMemberId(user.getId());
        model.addAttribute("chatRooms", chatRooms);

        return "domain/chat/chatRoom/list";
    }


    @Setter
    @Getter
    public static class WriteRequestBody {
        private String content;
    }

    @Getter
    @AllArgsConstructor
    public static class WriteResponseBody {
        private ChatMessage message;
    }

    @PostMapping("/{roomId}/write")
    @ResponseBody
    public RsData<?> write(
            @PathVariable final long roomId,
            @AuthenticationPrincipal SecurityUser user,
            @RequestBody final WriteRequestBody requestBody
    ) {
        ChatMessage chatMessage = chatRoomService.write(roomId, user.getName(), requestBody.getContent(), user.getId());

        RsData<WriteResponseBody> writeRs = RsData.of("S-1", "%d번 메시지를 작성하였습니다.".formatted(chatMessage.getId()), new WriteResponseBody(chatMessage));
//        RsData<WriteResponseBody> writeRs = RsData.of("S-MODIFIED", "%d번 메시지를 작성하였습니다.".formatted(chatMessage.getId()), new WriteResponseBody(chatMessage));
//        RsData<WriteResponseBody> writeRs = RsData.of("S-DELETED", "%d번 메시지를 작성하였습니다.".formatted(chatMessage.getId()), new WriteResponseBody(chatMessage));

        messagingTemplate.convertAndSend("/topic/chat/room/" + roomId + "/messageCreated", writeRs);

        return RsData.of("S-1", "성공");
    }

    //직접 상대방에게 신청하는 경우
    @GetMapping("/make/{theirInfo}")
    public String makeChatRoom(
            @AuthenticationPrincipal SecurityUser user,
            @PathVariable String theirInfo) {
        ChatRoomMemberInfoDto myInfoDto = new ChatRoomMemberInfoDto(user.getId(), user.getName());
        ChatRoomMemberInfoDto theirInfoDto = null;

        if (chatRoomService.isNumeric(theirInfo)) {
            theirInfoDto = chatRoomService.createInfoDtoByExperienceId(Long.parseLong(theirInfo));
        } else {
            theirInfoDto = chatRoomService.createInfoDtoByEmail(theirInfo);
        }
        Long chatRoomId;
        //존재 하는 방일 때 0보다 큰값이 return됨
        if((chatRoomId = chatRoomService.findChatRoom(user.getId(),theirInfoDto.getId()))<0){
            chatRoomId = chatRoomService.makeChatRoom(myInfoDto, theirInfoDto); //0보다 작으면 새로운 방 생성
        }

        return "redirect:/chat/room/" + chatRoomId;

    }

}