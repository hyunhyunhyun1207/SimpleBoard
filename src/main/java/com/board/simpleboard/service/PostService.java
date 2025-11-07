package com.board.simpleboard.service;

import com.board.simpleboard.dto.post.response.PostDetailRes;
import com.board.simpleboard.dto.post.response.PostListRes;
import com.board.simpleboard.dto.post.response.PostRes;

import java.util.List;

public interface PostService {
    void createPost(Long memberId, String title, String content, List<String> imageUuids);
    void saveImageInfo(String uuid, String fileName, String filePath, long fileSize, String contentType);
    PostListRes getPostList(int page);

    PostDetailRes getPostDetail(Long postId);
}
