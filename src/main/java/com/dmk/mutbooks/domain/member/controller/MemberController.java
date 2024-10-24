package com.dmk.mutbooks.domain.member.controller;

import com.dmk.mutbooks.domain.member.dto.MemberJoinRequest;
import com.dmk.mutbooks.domain.member.dto.MemberModifyRequest;
import com.dmk.mutbooks.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/modify")
    public String modifyForm(Principal principal, Model model) {
        // TODO: 회원 데이터를 모델에 담지 않고, 타임리프에서 시큐리티 태그를 통해 바로 가져오는 방식으로 전환
        model.addAttribute("member", memberService.getMemberByUsername(principal.getName()));
        return "member/modify";
    }

    @PostMapping("/modify")
    public String modify(Principal principal, MemberModifyRequest request) {
        memberService.modify(principal.getName(), request);
        return "redirect:/members/modify";
    }

    @GetMapping("/join")
    public String joinForm() {
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
