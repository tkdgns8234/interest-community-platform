package com.findy.user.infrastructure.repository.entity;

import com.findy.common.infrastructure.repository.entity.BaseTimeEntity;
import com.findy.user.domain.model.social.SocialAccount;
import com.findy.user.domain.model.social.Provider;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SocialAccountEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String email;
    String password;
    String providerId;

    @Enumerated(EnumType.STRING)
    Provider provider;

    public SocialAccountEntity(SocialAccount socialAccount) {
        this.id = socialAccount.getId();
        this.email = socialAccount.getEmail();
        this.password = socialAccount.getPassword();
        this.provider = socialAccount.getProvider();
        this.providerId = socialAccount.getProviderId();
    }

    public SocialAccount toSocialAccount() {
        return SocialAccount.create(id, email, password, provider, providerId);
    }
}
