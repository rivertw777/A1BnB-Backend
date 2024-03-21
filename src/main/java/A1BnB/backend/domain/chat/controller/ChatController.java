package A1BnB.backend.domain.chat.controller;

import A1BnB.backend.domain.chat.dto.request.ChatRequest;
import A1BnB.backend.domain.chat.dto.request.FindChatRoomRequest;
import A1BnB.backend.domain.chat.dto.response.ChatRoomResponse;
import A1BnB.backend.domain.chat.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    @MessageMapping("/messages") // publish
    public void sendMessage(@AuthenticationPrincipal(expression = "username") String username,
                            @Payload ChatRequest requestParam) {
        chatService.saveMessage(requestParam);
        // subscribe
        String destination = String.format("/subscribe/rooms/%s", requestParam.roomId());
        messagingTemplate.convertAndSend(destination, requestParam);
    }

    @PostMapping("api/chats")
    @ResponseBody
    public ChatRoomResponse getRoomInfo(@AuthenticationPrincipal(expression = "username") String username,
                                        @Valid @RequestBody FindChatRoomRequest requestParam) {
        return chatService.findRoom(username, requestParam);
    }

}