package com.dmk.mutbooks.domain.member.entity;

import com.dmk.mutbooks.common.jpa.AuditingFields;
import com.dmk.mutbooks.domain.member.dto.MemberJoinRequest;
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
    private String username;
    private String password;
    private String nickname;
    private String email;

    @Enumerated(EnumType.STRING)
    private AuthLevel authLevel;

    @Builder
    public Member(String username, String password, String email, AuthLevel authLevel) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.authLevel = authLevel;
    }

    public static Member of(MemberJoinRequest request, String encodedPassword) {
        return Member.builder()
                .username(request.getUsername())
                .password(encodedPassword)
                .email(request.getEmail())
                .authLevel(AuthLevel.NORMAL)
                .build();
    }
}
