package com.dmk.mutbooks.domain.member.model;

import com.dmk.mutbooks.common.model.AuditingFields;
import com.dmk.mutbooks.domain.member.dto.request.JoinRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthLevel authLevel;

    @Builder
    private Member(String username, String password, String email, String nickname, AuthLevel authLevel) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.authLevel = authLevel;
    }

    public static Member of(JoinRequest joinRequest, String encodedPassword) {
        return Member.builder()
                .username(joinRequest.getUsername())
                .password(encodedPassword)
                .email(joinRequest.getEmail())
                .nickname(null) // 회원가입시 닉네임은 받지 않기 때문에 null
                .authLevel(AuthLevel.NORMAL)
                .build();
    }

}
