package com.ll.feelko.domain.chat.chatRoom.controller;

import com.ll.feelko.domain.chat.chatMessage.api.request.WriteRequestBody;
import com.ll.feelko.domain.chat.chatMessage.service.ChatMessageService;
import com.ll.feelko.domain.chat.chatRoom.controller.request.ModifyRequestBody;
import com.ll.feelko.domain.chat.chatRoom.dto.ChatRoomListDto;
import com.ll.feelko.domain.chat.chatRoom.dto.ChatRoomMemberInfoDto;
import com.ll.feelko.domain.chat.chatRoom.entity.ChatRoom;
import com.ll.feelko.domain.chat.chatRoom.service.ChatRoomService;
import com.ll.feelko.global.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/chat/room")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;

    @GetMapping("/{roomId}")
    public String showRoom(
            @PathVariable final long roomId,
            @AuthenticationPrincipal SecurityUser user,
            Model model) {
        if (!chatRoomService.isIncludeMe(user.getId(), roomId)) {
            throw new RuntimeException("참여 권한이 없습니다.");
            //return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }

        return "domain/chat/chatRoom/room";
    }

    @GetMapping("/{roomId}/messages")
    @ResponseBody
    public ResponseEntity<?> showMessages(
            @PathVariable final long roomId,
            @AuthenticationPrincipal SecurityUser user){
        Optional<ChatRoom> chatRoomOptional = chatRoomService.findById(roomId);
        //TODO 페이지네이션으로 무한스크롤 구현
        if (chatRoomOptional.isPresent()) {
            ChatRoom chatRoom = chatRoomOptional.get();
            return ResponseEntity.ok(chatRoom.getChatMessages());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/list")
    public String showList(
            @AuthenticationPrincipal SecurityUser user,
            Model model) {
        List<ChatRoomListDto> chatRooms = chatRoomService.findByMemberId(user.getId());
        model.addAttribute("chatRooms", chatRooms);

        return "domain/chat/chatRoom/list";
    }

    @PostMapping("/{roomId}/write")
    @ResponseBody
    public ResponseEntity<?> write(
            @PathVariable final long roomId,
            @AuthenticationPrincipal SecurityUser user,
            @RequestBody final WriteRequestBody requestBody
    ) {
        chatMessageService.writeAndSend(roomId, user.getName(), requestBody.getContent(), "created", user.getId());

        return ResponseEntity.ok("성공");
    }

    //직접 상대방에게 신청하는 경우
    @GetMapping("/make/{theirInfo}")
    public String makeChatRoom(
            @AuthenticationPrincipal SecurityUser user,
            @PathVariable String theirInfo
    ) {
        ChatRoomMemberInfoDto myInfoDto = new ChatRoomMemberInfoDto(user.getId(), user.getName());
        ChatRoomMemberInfoDto theirInfoDto = null;

        if (chatRoomService.isNumeric(theirInfo)) {
            //숫자라면 experienceId
            theirInfoDto = chatRoomService.createInfoDtoByExperienceId(Long.parseLong(theirInfo));
            theirInfo = theirInfoDto.getId().toString(); //experienceId를 memberId로 바꾸는 작업
        } else {
            //숫자가 아니라면 email
            theirInfoDto = chatRoomService.createInfoDtoByEmail(theirInfo);
        }

        Long chatRoomId;
        //존재 하는 방일 때 0보다 큰값이 return됨
//        if((chatRoomId = chatRoomService.findChatRoom(user.getId(),theirInfoDto.getId()))>0){
//            return "redirect:/chat/room/" + chatRoomId;
//        }
//        chatRoomId = chatRoomService.makeChatRoom(myInfoDto, theirInfoDto); //0보다 작으면 새로운 방 생성
//        아이디 두개로 방여러개 만들어서 리스트 최신화 시험중 나중에 아래에 있는거 지우고 주석 해제하면 됩니다.
        chatRoomId = chatRoomService.makeChatRoom(myInfoDto, theirInfoDto);

        chatMessageService.writeAndSend(chatRoomId, user.getName(), "생성", "created", user.getId());

        return "redirect:/chat/room/" + chatRoomId;

    }

    @GetMapping("/exit/{chatRoomId}")
    @ResponseBody
    public ResponseEntity<?> exitChatRoom(
            @PathVariable Long chatRoomId,
            @AuthenticationPrincipal SecurityUser user) {

        if (!chatRoomService.isIncludeMe(user.getId(), chatRoomId)) {
            throw new RuntimeException("관련 권한이 없습니다.");
        }

        chatMessageService.writeAndSend(chatRoomId, user.getName(), "퇴장", "deleted", user.getId());

        if (!chatRoomService.exitChatRoomByMemberIdAndChatRoomId(user.getId(),chatRoomId)){
            throw new RuntimeException("채팅방을 나가는데 실패했습니다.");
        }

        return ResponseEntity.ok("성공");
    }

    @PostMapping("/modify/{chatRoomId}")
    @ResponseBody
    public ResponseEntity<?> modifyChatRoomName(
            @PathVariable Long chatRoomId,
            @AuthenticationPrincipal SecurityUser user,
            @RequestBody final ModifyRequestBody modifyBody) {

        if (!chatRoomService.isIncludeMe(user.getId(), chatRoomId)) {
            throw new RuntimeException("관련 권한이 없습니다.");
        }

        chatRoomService.modifyChatRoomName(user.getId(), chatRoomId, modifyBody.getChatRoomName());

        return ResponseEntity.ok("성공");
    }
}
