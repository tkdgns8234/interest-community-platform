package com.findy.user.domain.social;

import com.findy.common.out.repository.entity.BaseTimeEntity;
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
        validateEmail(email);
        validateProvider(provider);

        if (provider.isLocal()) {
            validatePassword(password);
            return new SocialAccount(id, email, password, provider, null);
        }

        validateProviderId(providerId);
        return new SocialAccount(id, email, null, provider, providerId);
    }

    public void updatePassword(String password) {
        validatePassword(password);
        this.password = password;
    }

    private static void validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
    }

    private static void validateProvider(Provider provider) {
        if (provider == null) {
            throw new IllegalArgumentException("Provider cannot be null");
        }
    }

    private static void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty for LOCAL provider");
        }
    }

    private static void validateProviderId(String providerId) {
        if (providerId == null || providerId.isEmpty()) {
            throw new IllegalArgumentException("ProviderId cannot be null or empty for social provider");
        }
    }
}
