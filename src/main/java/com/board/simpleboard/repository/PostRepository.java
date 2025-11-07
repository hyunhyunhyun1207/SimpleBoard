package com.board.simpleboard.repository;

import com.board.simpleboard.dto.post.response.PostDetailRes;
import com.board.simpleboard.dto.post.response.PostRes;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    // 게시글 등록
    public Long insertPost(Long memberId, String title, String content) {
        String sql = """
            INSERT INTO post (id, member_id, title, content, created_at)
            VALUES (post_seq.NEXTVAL, ?, ?, ?, SYSTIMESTAMP)
            """;
        jdbcTemplate.update(sql, memberId, title, content);
        return jdbcTemplate.queryForObject("SELECT post_seq.CURRVAL FROM dual", Long.class);
    }

    // 이미지 정보 저장
    public void insertImage(String uuid, String fileName, String filePath, long fileSize, String contentType) {
        String sql = """
            INSERT INTO post_image (
                id, uuid, post_id, file_name, file_path, file_size, content_type, created_at
            ) VALUES (
                post_image_seq.NEXTVAL, ?, NULL, ?, ?, ?, ?, SYSTIMESTAMP
            )
            """;
        jdbcTemplate.update(sql, uuid, fileName, filePath, fileSize, contentType);
    }

    // 이미지 post_id 업데이트
    public void updateImagePostId(Long postId, List<String> uuids) {
        String sql = "UPDATE post_image SET post_id = :postId WHERE uuid IN (:uuids)";
        Map<String, Object> params = new HashMap<>();
        params.put("postId", postId);
        params.put("uuids", uuids);
        namedParameterJdbcTemplate.update(sql, params);
    }

    public int countAllPosts() {
        String sql = "select count(*) from post";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public List<PostRes> findAllPostList(int startRow, int endRow) {
        String sql = """
        SELECT * FROM (
            SELECT p2.*, ROWNUM AS rn
            FROM (
SELECT p.id, p.title, case when p.member_id is null then '탈퇴한 사용자' else m.nickname end as nickname, p.created_at
                                        FROM post p
                                        left outer JOIN member m ON p.member_id = m.id
                                        ORDER BY p.id DESC
            ) p2
            WHERE ROWNUM <= ?
        )
        WHERE rn >= ?
        """;

        try {
            return jdbcTemplate.query(sql, (rs, rowNum) ->
                    new PostRes(
                            rs.getLong("id"),
                            rs.getString("title"),
                            rs.getString("nickname"),
                            rs.getTimestamp("created_at").toLocalDateTime()
                    ), endRow, startRow
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public PostDetailRes selectPostDetail(Long postId) {
        String sql = """
            SELECT p.id, p.title, p.content, case when member_id is null then '탈퇴한 사용자' else m.nickname end as nickname, p.created_at
            FROM post p
            left outer JOIN member m ON p.member_id = m.id
            WHERE p.id = ?
        """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> mapPost(rs), postId);
    }

    private PostDetailRes mapPost(ResultSet rs) throws SQLException {
        PostDetailRes res = new PostDetailRes();
        res.setId(rs.getLong("id"));
        res.setTitle(rs.getString("title"));
        res.setContent(rs.getString("content"));
        res.setWriter(rs.getString("nickname"));
        res.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return res;
    }
}
