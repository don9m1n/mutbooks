package com.dmk.mutbooks.common.model;

import com.dmk.mutbooks.domain.member.model.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUserDetails extends User {

    private final Long id;
    private final String email;
    private final String nickname;

    public CustomUserDetails(Member member, Collection<? extends GrantedAuthority> authorities) {
        super(member.getUsername(), member.getPassword(), authorities);
        this.id = member.getId();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
    }
}
