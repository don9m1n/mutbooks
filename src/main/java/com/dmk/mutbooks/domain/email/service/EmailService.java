package com.dmk.mutbooks.domain.email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Async
    public void sendMail(String email) {

        log.debug("축하 메일 발송! 수신자: {}", email);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(email); // 메일 수신자
            mimeMessageHelper.setSubject("멋북스 회원가입을 축하드립니다!");
            mimeMessageHelper.setText("축하드려요!!", true);

            javaMailSender.send(mimeMessage);

            log.debug("{}로 이메일 발송 완료!!!", email);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
