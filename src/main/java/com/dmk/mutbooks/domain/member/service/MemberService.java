package com.dmk.mutbooks.domain.member.service;

import com.dmk.mutbooks.common.model.CustomUserDetails;
import com.dmk.mutbooks.domain.email.service.EmailService;
import com.dmk.mutbooks.domain.member.dto.request.JoinRequest;
import com.dmk.mutbooks.domain.member.dto.request.ModifyRequest;
import com.dmk.mutbooks.domain.member.model.Member;
import com.dmk.mutbooks.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public void modify(Long memberId, ModifyRequest modifyRequest) {

        Member member = getMemberById(memberId);
        member.modify(modifyRequest.getEmail(), modifyRequest.getNickname());

        memberRepository.saveAndFlush(member);
        forceUpdateAuthentication(member);

    }

    public void join(JoinRequest joinRequest) {

        // 중복 회원 여부 검사
        memberRepository.findByUsername(joinRequest.getUsername()).ifPresent(member -> {
            throw new RuntimeException("이미 존재하는 회원입니다!");
        });

        Member member = Member.of(joinRequest, passwordEncoder.encode(joinRequest.getPassword()));
        memberRepository.save(member);

        emailService.sendMail(member.getEmail());

    }

    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다."));
    }

    private void forceUpdateAuthentication(Member member) {
        GrantedAuthority authority = new SimpleGrantedAuthority(member.getAuthLevel().name());;
        CustomUserDetails user = new CustomUserDetails(member, Collections.singleton(authority));

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, member.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
