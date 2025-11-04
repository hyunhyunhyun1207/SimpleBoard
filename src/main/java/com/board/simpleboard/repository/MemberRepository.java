package com.board.simpleboard.repository;

import com.board.simpleboard.domain.Member;
import com.board.simpleboard.dto.member.request.SignUpReq;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor

public class MemberRepository {
    private final JdbcTemplate jdbcTemplate;

    public void insertMember(SignUpReq signUpReq) {
        String sql = "insert into member (email, pwd, name, nickname) values (?, ?, ?, ?)";
        jdbcTemplate.update(sql, signUpReq.getEmail(),signUpReq.getPwd(),signUpReq.getName(),signUpReq.getNickName());
    }

    public Member findByEmail(String email) {
        String sql = "select * from member where email = ?";
        try {

            Member member = jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                    new Member(
                            rs.getLong(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getTimestamp(6).toLocalDateTime()
                    ), email
            );
            return member;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public Member findByNickname(String nickname) {
        String sql = "select * from member where nickname = ?";
        try {
            Member member = jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                    new Member(
                            rs.getLong(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getTimestamp(6).toLocalDateTime()
                    ), nickname
            );
            return member;
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }
 }
