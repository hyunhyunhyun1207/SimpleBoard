package com.board.simpleboard.dto.member.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

public class SignUpReq {
    private String email;
    private String pwd;
    private String name;
    private String nickName;
}
