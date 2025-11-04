package com.board.simpleboard.service;

import com.board.simpleboard.dto.member.request.LoginReq;
import com.board.simpleboard.dto.member.request.SignUpReq;
import com.board.simpleboard.dto.member.response.LoginRes;
import com.board.simpleboard.dto.member.response.SignUpRes;

public interface MemberService {
    SignUpRes createMember(SignUpReq signUpReq);
    LoginRes login(LoginReq loginReq);
}
