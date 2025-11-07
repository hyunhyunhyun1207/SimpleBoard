package com.board.simpleboard.dto.post.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PostRes {
    private Long id;
    private String title;
    private String nickname;
    private LocalDateTime createdAt;
}
