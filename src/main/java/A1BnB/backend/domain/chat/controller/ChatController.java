package A1BnB.backend.domain.chat.controller;

import A1BnB.backend.domain.chat.dto.request.ChatRequest;
import A1BnB.backend.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    @MessageMapping("/messages") // publish
    public void sendMessage(@AuthenticationPrincipal(expression = "username") String username,
                            @Payload ChatRequest requestParam) {
        // 채팅 내역 저장
        chatService.saveMessage(username, requestParam);
        String destination = String.format("/subscribe/rooms/%s", requestParam.roomId());
        messagingTemplate.convertAndSend(destination, requestParam);  // subscribe
    }

}