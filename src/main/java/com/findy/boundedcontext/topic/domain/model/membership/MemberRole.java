package com.findy.boundedcontext.topic.domain.model.membership;

public enum MemberRole {
    CREATOR,   // Topic creator - full permissions
    MANAGER,   // Topic manager - can manage members and content
    MEMBER     // Regular member - can participate in topic
    ;

    public boolean canManageTopic() {
        return this == CREATOR;
    }

    public boolean canManageMembers() {
        return this == CREATOR || this == MANAGER;
    }

    public boolean canParticipate() {
        return true; // All roles can participate
    }
}