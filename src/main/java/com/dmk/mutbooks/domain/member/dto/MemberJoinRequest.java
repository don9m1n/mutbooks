package com.dmk.mutbooks.domain.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberJoinRequest {

    private String username;
    private String password;

    // TODO: snake case -> camel case 로 변환해주는 방식 찾아보기
    private String passwordConfirm;
    private String email;

}
