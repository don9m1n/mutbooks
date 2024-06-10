package com.dmk.mutbooks.domain.member.controller;

import com.dmk.mutbooks.domain.member.dto.request.JoinRequest;
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
    public String joinForm(JoinRequest joinRequest) {

        return "members/join";
    }

    @PostMapping("/join")
    public String join(JoinRequest joinRequest) {

        memberService.join(joinRequest);
        return "redirect:/members/login";
    }
}
