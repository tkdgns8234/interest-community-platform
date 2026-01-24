package com.findy.user.domain.model.social;

public enum Provider {
    LOCAL,
    GOOGLE,
    FACEBOOK,
    GITHUB;

    public boolean isLocal() {
        return this == LOCAL;
    }
}
