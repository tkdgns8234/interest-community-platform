package com.findy.user.out.repository.entity;

import com.findy.common.out.repository.entity.BaseTimeEntity;
import com.findy.user.domain.model.User;
import com.findy.user.domain.model.UserInfo;
import com.findy.user.domain.model.FollowManager;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String nickname;
    private String profileImageUrl;
    private Long followerCount;
    private Long followingCount;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private SocialAccountEntity socialAccountEntity;

    public UserEntity(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.profileImageUrl = user.getProfileImageUrl();
        this.followerCount = user.getFollowManager().getFollowerCount();
        this.followingCount = user.getFollowManager().getFollowingCount();
        this.socialAccountEntity = new SocialAccountEntity(user.getSocialAccount());
    }

    public User toUser() {
        return User.builder()
                .id(id)
                .userInfo(new UserInfo(name, nickname, profileImageUrl))
                .followManager(new FollowManager(followerCount, followingCount))
                .socialAccount(socialAccountEntity.toSocialAccount())
                .build();
    }
}
