package com.board.simpleboard.dto.post.response;

import com.board.simpleboard.dto.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PostListRes {
    private List<PostRes> postList;
    private PageInfo pageInfo;
}
