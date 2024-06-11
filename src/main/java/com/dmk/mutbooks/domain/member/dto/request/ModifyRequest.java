package com.dmk.mutbooks.domain.member.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ModifyRequest {

    private String email;
    private String nickname;

}
