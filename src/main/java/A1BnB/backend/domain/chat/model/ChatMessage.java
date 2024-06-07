package A1BnB.backend.domain.chat.model;

import A1BnB.backend.global.model.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "chatMessages")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ChatRoomId")
    private ChatRoom chatRoom;

    @NotNull
    @Column(name = "senderName")
    private String senderName;

    @NotNull
    @Column(name = "message")
    private String message;

    @Builder
    public ChatMessage(ChatRoom chatRoom, String senderName, String message) {
        setChatRoom(chatRoom);
        this.senderName = senderName;
        this.message = message;
    }

    private void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
        chatRoom.setChatMessages(this);
    }

}