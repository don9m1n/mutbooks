package com.dmk.mutbooks.domain.member.controller;

import com.dmk.mutbooks.common.model.CustomUserDetails;
import com.dmk.mutbooks.domain.member.dto.request.JoinRequest;
import com.dmk.mutbooks.domain.member.dto.request.ModifyRequest;
import com.dmk.mutbooks.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify")
    public String modifyForm() {
        return "members/modify";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify")
    public String modify(@AuthenticationPrincipal CustomUserDetails user, ModifyRequest modifyRequest) {

        log.debug("현재 로그인한 회원 정보의 id: {}", user.getId());
        log.debug("요청 데이터: {}", modifyRequest);

        memberService.modify(user.getId(), modifyRequest);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "members/login";
    }

    @GetMapping("/join")
    public String joinForm(JoinRequest joinRequest) {

        return "members/join";
    }

    @PostMapping("/join")
    public String join(JoinRequest joinRequest) {

        memberService.join(joinRequest);
        return "redirect:/members/login";
    }
}
