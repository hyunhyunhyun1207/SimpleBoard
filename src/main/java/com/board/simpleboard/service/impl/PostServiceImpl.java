package com.board.simpleboard.service.impl;

import com.board.simpleboard.dto.common.PageInfo;
import com.board.simpleboard.dto.post.response.PostDetailRes;
import com.board.simpleboard.dto.post.response.PostListRes;
import com.board.simpleboard.dto.post.response.PostRes;
import com.board.simpleboard.repository.PostRepository;
import com.board.simpleboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    @Transactional
    public void createPost(Long memberId, String title, String content, List<String> imageUuids) {
        // 게시글 등록
        Long postId = postRepository.insertPost(memberId, title, content);

        // 업로드된 이미지들 post_id 업데이트
        if (imageUuids != null && !imageUuids.isEmpty()) {
            postRepository.updateImagePostId(postId, imageUuids);
        }
    }

    @Override
    public void saveImageInfo(String uuid, String fileName, String filePath, long fileSize, String contentType) {
        postRepository.insertImage(uuid, fileName, filePath, fileSize, contentType);
    }

    @Override
    public PostListRes getPostList(int page) {
        // PageInfo에 들어갈 값들 설명
        // 총 게시글 수 123 현재 4페이지로 가정

        // 한 페이지당 게시글 수
        int postsPerPage = 10;
        // 페이지 단위 ex < 1 2 3 4 5 >
        int pageBlock = 5;

        // 전체 게시글 수 (예: 총 123개)
        int totalCount = postRepository.countAllPosts();

        // 전체 페이지 수 계산
        // 전체 페이지 수 = 123 ÷ 10 = 12.3 → 올림(Math.ceil)해서 13페이지
        // 즉, 페이지는 1~13까지 존재
        int totalPage = (int)Math.ceil((double) totalCount / postsPerPage);

        // 페이지 번호가 유효 범위를 벗어날 경우 보정
        if (page < 1) page = 1; // 최소 1페이지
        if (page > totalPage) page = totalPage; // 최대 13페이지

        // rownum에 사용할 시작행, 끝행 계산
        // 현재 페이지가 4라면 → (4 - 1) * 10 + 1 = 31, endRow = 4 * 10 = 40
        // 즉, 31번부터 40번 게시글 조회
        int startRow = (page - 1) * postsPerPage + 1;
        int endRow = page * postsPerPage;

        // 현재 페이지 블록 계산
        // 현재 페이지 4 기준: ceil(4 / 5.0) = 1 → 1번째 블록
        // startPage = (1 - 1) * 5 + 1 = 1, endPage = 1 + 5 - 1 = 5
        // 즉, 1~5 페이지 버튼 표시
        int currentBlock = (int) Math.ceil((double) page / pageBlock);
        int startPage = (currentBlock - 1) * pageBlock + 1;
        int endPage = startPage + pageBlock - 1;
        if (endPage > totalPage) endPage = totalPage;

        // 이전/다음 버튼 표시 여부
        // prev: 현재 페이지가 1보다 크면 true
        // next: 현재 페이지가 전체 페이지보다 작으면 true
        // 현재 페이지 4 기준 → 이전 true, 다음 true
        // (1 페이지면 prev가 false라서 이전 버튼이 없겠죠?)
        boolean prev = page > 1;
        boolean next = page < totalPage;

        PageInfo pageInfo = new PageInfo(page, startPage, endPage, totalPage, prev, next);

        PostListRes res = new PostListRes();
        List<PostRes> list = postRepository.findAllPostList(startRow, endRow);
        res.setPostList(list);
        res.setPageInfo(pageInfo);
        return res;
    }

    @Transactional(readOnly = true)
    @Override
    public PostDetailRes getPostDetail(Long postId) {
        return postRepository.selectPostDetail(postId);
    }
}