package com.board.simpleboard.controller;

import com.board.simpleboard.dto.member.response.LoginRes;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")

public class MypageController {

    @GetMapping
    public String MyPage(Model model, HttpSession session) {
        if (session.getAttribute("member") == null) {
            return "redirect:/";
        }
        return "mypage/mypage";
    }
}
