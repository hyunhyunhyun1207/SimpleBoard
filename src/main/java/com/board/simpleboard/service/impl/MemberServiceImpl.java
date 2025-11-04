package com.board.simpleboard.service.impl;

import com.board.simpleboard.domain.Member;
import com.board.simpleboard.dto.member.request.LoginReq;
import com.board.simpleboard.dto.member.request.SignUpReq;
import com.board.simpleboard.dto.member.response.LoginRes;
import com.board.simpleboard.dto.member.response.SignUpRes;
import com.board.simpleboard.repository.MemberRepository;
import com.board.simpleboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SignUpRes createMember(SignUpReq signUpReq) {
        Member memberByEmail = memberRepository.findByEmail(signUpReq.getEmail());
        SignUpRes signUpRes = new SignUpRes();
        signUpRes.setSignUp(true);
        if (memberByEmail != null) {
            signUpRes.setSignUp(false);
            signUpRes.setEmailError("이미 사용 중인 이메일 입니다.");
        }
        Member memberByNickname = memberRepository.findByNickname(signUpReq.getNickName());
        if (memberByNickname != null) {
            signUpRes.setSignUp(false);
            signUpRes.setNicknameError("이미 사용 중인 닉네임 입니다.");
        }
        if (!signUpRes.isSignUp()) return signUpRes;
        String encodePwd = passwordEncoder.encode(signUpReq.getPwd());
        signUpReq.setPwd(encodePwd);
        memberRepository.insertMember(signUpReq);
        return signUpRes;
    }

    @Override
    public LoginRes login(LoginReq loginReq) {
        Member member = memberRepository.findByEmail(loginReq.getEmail());
        LoginRes loginRes = new LoginRes();
        if (member != null) {
            if(passwordEncoder.matches(loginReq.getPwd(), member.getPwd())) {
                loginRes.setId(member.getId());
                loginRes.setEmail(member.getEmail());
                loginRes.setName(member.getName());
                loginRes.setNickName(member.getNickName());
                loginRes.setLogin(true);
                return loginRes;
            }
        }
        loginRes.setLogin(false);
        loginRes.setLoginFail("회원 정보를 다시 확인하세요");
        return loginRes;
    }
}
