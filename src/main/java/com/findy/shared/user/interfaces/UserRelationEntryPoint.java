package com.findy.shared.user.interfaces;

import java.util.List;

public interface UserRelationEntryPoint {
    /**
     * return: List of userId
     */
    List<Long> findFollowers(Long userId, Long cursor, int pageSize);
}
