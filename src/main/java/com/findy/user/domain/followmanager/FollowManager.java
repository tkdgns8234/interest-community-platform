package com.findy.user.domain.followmanager;

import com.findy.common.domain.IntegerCounter;

public class FollowManager {
    IntegerCounter followerCount;
    IntegerCounter followingCount;

    public FollowManager() {
        this.followerCount = new IntegerCounter();
        this.followingCount = new IntegerCounter();
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
}
