package com.findy.user.out.repository.entity;

import com.findy.common.out.repository.entity.BaseTimeEntity;
import com.findy.user.domain.User;
import com.findy.user.domain.UserInfo;
import com.findy.user.domain.followmanager.FollowManager;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String nickname;
    private String profileImageUrl;
    private Long followerCount;
    private Long followingCount;

    public UserEntity(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.profileImageUrl = user.getProfileImageUrl();
        this.followerCount = user.getFollowManager().getFollowerCount();
        this.followingCount = user.getFollowManager().getFollowingCount();
    }

    public User toUser() {
        return User.builder()
                .id(id)
                .userInfo(new UserInfo(name, nickname, profileImageUrl))
                .followManager(new FollowManager(followerCount, followingCount))
                .build();
    }
}
