package com.dmk.mutbooks.domain.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class MemberModifyRequest {

    private String username;
    private String email;
    private String nickname;

}
