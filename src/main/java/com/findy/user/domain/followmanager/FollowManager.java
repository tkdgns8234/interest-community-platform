package com.findy.user.domain.followmanager;

import com.findy.common.domain.IntegerCounter;

public class FollowManager {
    IntegerCounter followerCount;
    IntegerCounter followingCount;

    public FollowManager() {
        this.followerCount = new IntegerCounter();
        this.followingCount = new IntegerCounter();
    }

    public FollowManager(long followerCount, long followingCount) {
        this.followerCount = new IntegerCounter(followerCount);
        this.followingCount = new IntegerCounter(followingCount);
    }

    public void increaseFollowingCount() {
        followingCount.increase();
    }

    public void decreaseFollowingCount() {
        followingCount.decrease();
    }

    public void increaseFollowerCount() {
        followerCount.increase();
    }

    public void decreaseFollowerCount() {
        followerCount.decrease();
    }

    public long getFollowerCount() {
        return followerCount.getCount();
    }

    public long getFollowingCount() {
        return followingCount.getCount();
    }
}
