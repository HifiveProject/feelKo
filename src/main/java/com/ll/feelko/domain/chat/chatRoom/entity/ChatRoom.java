package com.ll.feelko.domain.chat.chatRoom.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.feelko.domain.chat.chatMessage.entity.ChatMessage;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Setter
@Getter
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@SuperBuilder
@ToString(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class ChatRoom{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @CreatedDate
    @Getter
    private LocalDateTime createDate;

    @LastModifiedDate
    @Getter
    private LocalDateTime modifyDate;

    private String name;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    @OrderBy("id DESC")
    @JsonIgnore
    private List<ChatMessage> chatMessages = new ArrayList<>();


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // 무한 재귀 방지
    private List<ChatRoomMember> chatRoomMembers;

}
