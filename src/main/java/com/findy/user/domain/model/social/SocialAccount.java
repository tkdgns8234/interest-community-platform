package com.findy.user.domain.model.social;

import com.findy.common.infrastructure.repository.entity.BaseTimeEntity;
import lombok.Getter;

@Getter
public class SocialAccount extends BaseTimeEntity {
    Long id;
    String email;
    String password;
    Provider provider;
    String providerId;

    public SocialAccount() {}

    private SocialAccount(Long id, String email, String password, Provider provider, String providerId) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.provider = provider;
        this.providerId = providerId;
    }

    public static SocialAccount create(Long id, String email, String password, Provider provider, String providerId) {
        if (provider.isLocal()) {
            return new SocialAccount(id, email, password, provider, null);
        }

        return new SocialAccount(id, email, null, provider, providerId);
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
