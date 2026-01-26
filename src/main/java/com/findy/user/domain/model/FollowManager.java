package com.findy.user.domain.model;

import com.findy.common.domain.PositiveIntegerCounter;

/**
 * FollowManager
 * 팔로워 수와 팔로잉 수를 관리하는 클래스
 * ValueObject 적용을 고려하였으나, User 도메인의 내부 구현을 캡슐화한 객체에 더 가깝다고 판단. 현재 구현 유지
 */
public class FollowManager {
    private PositiveIntegerCounter followerCount;
    private PositiveIntegerCounter followingCount;

    public FollowManager() {
        this.followerCount = new PositiveIntegerCounter();
        this.followingCount = new PositiveIntegerCounter();
    }

    public FollowManager(long followerCount, long followingCount) {
        this.followerCount = new PositiveIntegerCounter(followerCount);
        this.followingCount = new PositiveIntegerCounter(followingCount);
    }

    public void increaseFollowingCount() {
        followingCount = followingCount.increase();
    }

    public void decreaseFollowingCount() {
        followingCount = followingCount.decrease();
    }

    public void increaseFollowerCount() {
        followerCount = followerCount.increase();
    }

    public void decreaseFollowerCount() {
        followerCount = followerCount.decrease();
    }

    public long getFollowerCount() {
        return followerCount.getCount();
    }

    public long getFollowingCount() {
        return followingCount.getCount();
    }
}
