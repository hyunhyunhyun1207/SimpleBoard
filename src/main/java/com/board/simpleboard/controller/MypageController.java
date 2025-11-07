package com.board.simpleboard.controller;

import com.board.simpleboard.dto.member.response.LoginRes;
import com.board.simpleboard.service.MypageService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor

public class MypageController {
    private final MypageService mypageService;

    @GetMapping
    public String MyPage(Model model, HttpSession session) {
        if (session.getAttribute("member") == null) {
            return "redirect:/";
        }
        return "mypage/mypage";
    }
    @GetMapping("/nickname")
    public String nickname() {
        return "mypage/nickname";
    }
    @PostMapping("/nickname")
    public String nickname(@RequestParam String nickname,HttpSession session, Model model) {
        LoginRes loginRes =(LoginRes) session.getAttribute("member"); // 형변환
        System.out.println(nickname);
        System.out.println(loginRes.getId());
        boolean result = mypageService.updateNickname(nickname, loginRes.getId());
//        if (mypageService.updateNickname(nickname, loginRes.getId()))
        if (result) {
            loginRes.setNickName(nickname);
            session.setAttribute("member", loginRes);
            return "mypage/mypage";
        }
        model.addAttribute("errorMsg", "이미 사용 중인 닉네임 입니다.");
        return "mypage/nickname";

    }
}
