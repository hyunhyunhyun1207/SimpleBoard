package com.board.simpleboard.controller;

import com.board.simpleboard.dto.member.request.LoginReq;
import com.board.simpleboard.dto.member.request.SignUpReq;
import com.board.simpleboard.dto.member.response.LoginRes;
import com.board.simpleboard.dto.member.response.SignUpRes;
import com.board.simpleboard.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor

public class MemberController {
    private final MemberService memberService;

    @GetMapping("/signup")
    public String signUp(Model model, HttpSession session) {
        if (session.getAttribute("member") != null) {
            return "redirect:/";
        }
        model.addAttribute("signupReq", new SignUpReq());
        return "/member/signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("signupReq") SignUpReq signUpReq, Model model) {
        SignUpRes signUpRes = memberService.createMember(signUpReq);
        if (!signUpRes.isSignUp()) {
            model.addAttribute("errorEmail", signUpRes.getEmailError());
            model.addAttribute("errorNickName", signUpRes.getNicknameError());
            return "/member/signup";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/login")
    public String login(Model model, HttpSession session) {
        if (session.getAttribute("member") != null) {
            return "redirect:/";
        }
        model.addAttribute("loginReq",new LoginReq());
        return "member/login";
    }

    @PostMapping("/login")
    public String loginP(@ModelAttribute("loginReq") LoginReq loginReq, Model model, HttpSession session) {
        LoginRes loginRes = memberService.login(loginReq);
        if (!loginRes.isLogin()) {
            model.addAttribute("errorMsg",loginRes.getLoginFail());
            return "member/login";
        }
        session.setAttribute("member", loginRes);
        return "redirect:/";
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        if (session.getAttribute("member") != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
