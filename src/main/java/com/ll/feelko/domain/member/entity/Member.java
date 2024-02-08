package com.ll.feelko.domain.member.entity;

import com.ll.feelko.domain.chat.chatRoom.entity.ChatRoomMember;
import com.ll.feelko.domain.wishlist.entity.WishList;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Getter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Email(message = "유효한 이메일 형식을 입력해주세요.")
    private String email;
    private String password;
    private String profile;
    private String phone;
    private LocalDate birthday;
    private String roles;

    @CreatedDate
    private LocalDateTime created_at;
    @LastModifiedDate
    private LocalDateTime modified_at;

    private LocalDateTime deleted_at;

    private String providerId;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));

        if (List.of("admin").contains(roles)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return authorities;
    }

    public boolean isAdmin() {
        return List.of("admin").contains(roles);
    }

//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "member_id")
//    private List<Payment> payments;
//
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "member_id")
//    private List<Experience> experiences;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "member")
    private List<WishList> wishLists;

    //보류
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "member_id")
//    private List<PaymentDetail> paymentDetails;

    // 회원정보 수정 메소드
    public void updateProfile(String profile) {
        this.profile = profile;
    }

    public void profileUpdate(String name, String profile, String phone, LocalDate birthday) {
        this.name = name;
        this.profile = profile;
        this.phone = phone;
        this.birthday = birthday;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatRoomMember> chatRoomMembers;

}
