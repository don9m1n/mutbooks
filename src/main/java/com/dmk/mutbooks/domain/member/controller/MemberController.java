package com.dmk.mutbooks.domain.member.controller;

import com.dmk.mutbooks.domain.member.dto.MemberJoinRequest;
import com.dmk.mutbooks.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String join() {
        return "member/join";
    }

    @PostMapping("/join")
    public String join(MemberJoinRequest request) {
        memberService.join(request);
        return "redirect:/members/login";
    }

    @GetMapping("/login")
    public String login() {
        return "member/login";
    }
}
