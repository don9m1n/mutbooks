package com.dmk.mutbooks.common.service;

import com.dmk.mutbooks.domain.member.model.AuthLevel;
import com.dmk.mutbooks.domain.member.model.Member;
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

import java.util.LinkedHashSet;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.debug("일반 로그인 실행! 요청 아이디: {}", username);
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다!"));

        Set<GrantedAuthority> authorities = new LinkedHashSet<>();

        switch (member.getAuthLevel()) {
            case NORMAL -> authorities.add(new SimpleGrantedAuthority("ROLE_NORMAL"));
            case AUTHOR -> authorities.add(new SimpleGrantedAuthority("ROLE_AUTHOR"));
            case ADMIN -> authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return new User(member.getUsername(), member.getPassword(), authorities);
    }
}
