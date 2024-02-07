package com.ll.feelko.domain.chat.chatRoom.controller;

import com.ll.feelko.domain.chat.chatMessage.entity.ChatMessage;
import com.ll.feelko.domain.chat.chatMessage.service.ChatMessageService;
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

    //지우던가 public private필드 만들어서 채팅방 접근시에 확인하게 만들기
    @GetMapping("/make")
    public String showMake() {
        return "domain/chat/chatRoom/make";
    }

    @PostMapping("/make")
    public String make(
            final String name
    ) {
        chatRoomService.make(name);

        return "redirect:/chat/room/list";
    }

    @GetMapping("/list")
    public String showList(
            @AuthenticationPrincipal SecurityUser user,
            Model model) {
        List<ChatRoom> chatRooms = chatRoomService.findByMemberId(user.getId());
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

        Long chatRoomId = chatRoomService.makeChatRoom(myInfoDto, theirInfoDto);

        return "redirect:/chat/room/" + chatRoomId;

    }

}
