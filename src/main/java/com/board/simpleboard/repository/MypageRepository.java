package com.board.simpleboard.repository;

import com.board.simpleboard.dto.member.request.SignUpReq;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor

public class MypageRepository {
    private final JdbcTemplate jdbcTemplate;


    public boolean findByNickname(String nickname) {
        String sql = "select count(*) from member where nickname = ?";
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class, nickname); // 단일행 반환시 qFO
        // result에는 0 or 1이라는 값이 담김 그리고 0은 중복이 아님 1은 중복임
        return result == 0; // 0이면 true 0아 아니면 false
    }

    public void updateNickname(String nickname, Long id) {
        String sql = "update member set nickname = ? where id = ?";
        jdbcTemplate.update(sql, nickname, id);
    }
}
