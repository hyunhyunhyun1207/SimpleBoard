package com.board.simpleboard.dto.member.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

public class SignUpRes {
    private boolean isSignUp;
    private String emailError;
    private String nicknameError;
}
