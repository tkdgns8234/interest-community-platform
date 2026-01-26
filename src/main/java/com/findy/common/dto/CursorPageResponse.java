package com.findy.common.dto;

import java.util.List;

public record CursorPageResponse<T extends Identifiable>(
    List<T> content,
    Long nextCursor,
    boolean hasNext
) {
    /**
     * @param content 조회된 데이터 리스트
     * - content 파라미터의 데이터 수는 size + 1로, 다음 페이지 존재 여부를 판단하기 위함
     * @param size 페이지 크기
     */
    public static <T extends Identifiable> CursorPageResponse<T> of(List<T> content, int size) {
        boolean hasNext = content.size() > size;
        List<T> result = hasNext ? content.subList(0, size) : content;
        Long nextCursor = hasNext ? result.get(result.size() - 1).id() : null;

        return new CursorPageResponse<>(result, nextCursor, hasNext);
    }
}