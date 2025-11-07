package com.board.simpleboard.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PostImage {
    private Long id;
    private Long postId;
    private String fileName;
    private String imageUrl;
    private Long fileSize;
    private String contentType;
    private String createdAt;
}
