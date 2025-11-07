package com.board.simpleboard.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PageInfo {
    private int currentPage; // 현재 페이지 번호
    private int startPage; // 시작 페이지
    private int endPage; // 끝페이지
    private int totalPage; // 전체 페이지 수
    private boolean prev; // 이전 페이지 존재 여부 (ex 1페이지에선 이전 페이지가 없겠지?)
    private boolean next; // 다음 페이지 존재 여부 (ex 마지막페이지에선 다음 페이지가 없겠지?)
}
