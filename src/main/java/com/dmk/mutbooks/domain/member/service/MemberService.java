package com.dmk.mutbooks.domain.member.service;

import com.dmk.mutbooks.domain.email.service.EmailService;
import com.dmk.mutbooks.domain.member.dto.request.JoinRequest;
import com.dmk.mutbooks.domain.member.model.Member;
import com.dmk.mutbooks.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public void join(JoinRequest joinRequest) {

        // 중복 회원 여부 검사
        memberRepository.findByUsername(joinRequest.getUsername()).ifPresent(member -> {
            throw new RuntimeException("이미 존재하는 회원입니다!");
        });

        Member member = Member.of(joinRequest, passwordEncoder.encode(joinRequest.getPassword()));
        memberRepository.save(member);

        emailService.sendMail(member.getEmail());

    }
}
