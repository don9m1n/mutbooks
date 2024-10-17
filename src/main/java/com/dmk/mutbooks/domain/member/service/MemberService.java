package com.dmk.mutbooks.domain.member.service;

import com.dmk.mutbooks.domain.member.dto.MemberJoinRequest;
import com.dmk.mutbooks.domain.member.entity.Member;
import com.dmk.mutbooks.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
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
}
