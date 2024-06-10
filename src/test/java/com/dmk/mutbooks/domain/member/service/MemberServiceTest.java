package com.dmk.mutbooks.domain.member.service;

import com.dmk.mutbooks.domain.email.service.EmailService;
import com.dmk.mutbooks.domain.member.dto.request.JoinRequest;
import com.dmk.mutbooks.domain.member.model.Member;
import com.dmk.mutbooks.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;

@DisplayName("비즈니스 로직 테스트 - 회원")
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService sut;

    @Mock
    private EmailService emailService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @DisplayName("회원 정보를 입력하면, 회원가입이 완료된다.")
    @Test
    void givenMemberInfo_whenJoining_thenSaveMember() {
        // given
        JoinRequest joinRequest = createJoinRequest();
        given(memberRepository.findByUsername(joinRequest.getUsername())).willReturn(Optional.empty());
        given(passwordEncoder.encode(anyString())).willReturn("encrypt_password");
        given(memberRepository.save(any(Member.class))).willReturn(mock(Member.class));
        willDoNothing().given(emailService).sendMail(joinRequest.getEmail());

        // when
        sut.join(joinRequest);

        // then
        then(memberRepository).should().findByUsername(joinRequest.getUsername());
        then(passwordEncoder).should().encode(anyString());
        then(memberRepository).should().save(any(Member.class));
        then(emailService).should().sendMail(joinRequest.getEmail());
    }

    private JoinRequest createJoinRequest() {
        return new JoinRequest("user1", "asdf1234", "user1@email.com");
    }

}