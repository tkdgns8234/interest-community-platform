package com.findy.topic.domain.model.topic;

import com.findy.common.domain.PositiveIntegerCounter;
import lombok.Getter;

@Getter
public class MembershipManager {
    private PositiveIntegerCounter memberCount;

    public MembershipManager() {
        this.memberCount = new PositiveIntegerCounter(1); // Creator is first member
    }

    public MembershipManager(long initialCount) {
        this.memberCount = new PositiveIntegerCounter(initialCount);
    }

    public void addMember() {
        this.memberCount = this.memberCount.increase();
    }

    public void removeMember() {
        this.memberCount = this.memberCount.decrease();
    }

    public long getMemberCount() {
        return this.memberCount.getCount();
    }
}
