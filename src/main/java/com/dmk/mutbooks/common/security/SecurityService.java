package com.dmk.mutbooks.common.security;

import com.dmk.mutbooks.domain.member.entity.AuthLevel;
import com.dmk.mutbooks.domain.member.entity.Member;
import com.dmk.mutbooks.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.debug("스프링 시큐리티 form 로그인 실행 - username: {}", username);

        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다."));

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (member.getAuthLevel().equals(AuthLevel.NORMAL)) {
            authorities.add(new SimpleGrantedAuthority(AuthLevel.NORMAL.name()));
        } else {
            authorities.add(new SimpleGrantedAuthority(AuthLevel.ADMIN.name()));
        }

        return new User(member.getUsername(), member.getPassword(), authorities);
    }
}
