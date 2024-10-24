package com.dmk.mutbooks.domain.member.service;

import com.dmk.mutbooks.domain.member.dto.MemberJoinRequest;
import com.dmk.mutbooks.domain.member.dto.MemberModifyRequest;
import com.dmk.mutbooks.domain.member.entity.Member;
import com.dmk.mutbooks.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void modify(String username, MemberModifyRequest request) {
        if(!Objects.equals(username, request.getUsername())) {
            throw new RuntimeException("유저 정보가 일치하지 않습니다.");
        }

        Member member = getMemberByUsername(username);
        member.modify(request.getNickname(), request.getEmail());

        memberRepository.save(member);

        // TODO: 시큐리티 세션에 변경된 데이터 동기화!
    }

    public void join(MemberJoinRequest request) {

        Optional<Member> findMember = memberRepository.findByUsername(request.getUsername());
        if (findMember.isPresent()) {
            throw new RuntimeException("Member already exists");
        }

        if (!Objects.equals(request.getPassword(), request.getPasswordConfirm())) {
            throw new RuntimeException("Passwords do not match");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Member member = Member.of(request, encodedPassword);

        memberRepository.save(member);

        // TODO: 회원 가입 후 축하 메일 발송
    }

    @Transactional(readOnly = true)
    public Member getMemberByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다."));
    }
}
