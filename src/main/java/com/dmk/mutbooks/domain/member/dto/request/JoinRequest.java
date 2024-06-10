package com.dmk.mutbooks.domain.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JoinRequest {

    // TODO: 유효성 처리 필요
    private String username;
    private String password;
    private String email;

}
