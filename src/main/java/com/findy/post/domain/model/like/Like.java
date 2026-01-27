package com.findy.post.domain.model.like;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Like {
    private final Long id;
    private final Long userId;
    private final Long targetId;
    private final TargetType targetType;
}
