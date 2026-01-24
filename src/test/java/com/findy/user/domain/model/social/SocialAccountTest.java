package com.findy.user.domain.model.social;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SocialAccountTest {

    @Nested
    @DisplayName("LOCAL provider로 계정 생성")
    class LocalProvider {

        @Test
        @DisplayName("유효한 이메일과 비밀번호로 LOCAL 계정을 생성할 수 있다")
        void createLocalAccountWithValidEmailAndPassword() {
            SocialAccount account = SocialAccount.create(1L, "test@example.com", "password123", Provider.LOCAL, null);

            assertThat(account.getId()).isEqualTo(1L);
            assertThat(account.getEmail()).isEqualTo("test@example.com");
            assertThat(account.getPassword()).isEqualTo("password123");
            assertThat(account.getProvider()).isEqualTo(Provider.LOCAL);
            assertThat(account.getProviderId()).isNull();
        }

        @Test
        @DisplayName("LOCAL provider에서 비밀번호가 null이면 예외가 발생한다")
        void throwExceptionWhenPasswordIsNullForLocal() {
            assertThatThrownBy(() -> SocialAccount.create(1L, "test@example.com", null, Provider.LOCAL, null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Password cannot be null or empty for LOCAL provider");
        }

        @Test
        @DisplayName("LOCAL provider에서 비밀번호가 빈 문자열이면 예외가 발생한다")
        void throwExceptionWhenPasswordIsEmptyForLocal() {
            assertThatThrownBy(() -> SocialAccount.create(1L, "test@example.com", "", Provider.LOCAL, null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Password cannot be null or empty for LOCAL provider");
        }
    }

    @Nested
    @DisplayName("소셜 provider로 계정 생성")
    class SocialProvider {

        @Test
        @DisplayName("GOOGLE provider로 계정을 생성할 수 있다")
        void createGoogleAccount() {
            SocialAccount account = SocialAccount.create(1L, "test@gmail.com", null, Provider.GOOGLE, "google-123");

            assertThat(account.getEmail()).isEqualTo("test@gmail.com");
            assertThat(account.getPassword()).isNull();
            assertThat(account.getProvider()).isEqualTo(Provider.GOOGLE);
            assertThat(account.getProviderId()).isEqualTo("google-123");
        }

        @Test
        @DisplayName("소셜 provider에서 providerId가 null이면 예외가 발생한다")
        void throwExceptionWhenProviderIdIsNullForSocialProvider() {
            assertThatThrownBy(() -> SocialAccount.create(1L, "test@gmail.com", null, Provider.GOOGLE, null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("ProviderId cannot be null or empty for social provider");
        }

        @Test
        @DisplayName("소셜 provider에서 providerId가 빈 문자열이면 예외가 발생한다")
        void throwExceptionWhenProviderIdIsEmptyForSocialProvider() {
            assertThatThrownBy(() -> SocialAccount.create(1L, "test@gmail.com", null, Provider.GOOGLE, ""))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("ProviderId cannot be null or empty for social provider");
        }
    }

    @Nested
    @DisplayName("공통 검증")
    class CommonValidation {

        @Test
        @DisplayName("이메일이 null이면 예외가 발생한다")
        void throwExceptionWhenEmailIsNull() {
            assertThatThrownBy(() -> SocialAccount.create(1L, null, "password", Provider.LOCAL, null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Email cannot be null or empty");
        }

        @Test
        @DisplayName("이메일이 빈 문자열이면 예외가 발생한다")
        void throwExceptionWhenEmailIsEmpty() {
            assertThatThrownBy(() -> SocialAccount.create(1L, "", "password", Provider.LOCAL, null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Email cannot be null or empty");
        }

        @Test
        @DisplayName("provider가 null이면 예외가 발생한다")
        void throwExceptionWhenProviderIsNull() {
            assertThatThrownBy(() -> SocialAccount.create(1L, "test@example.com", "password", null, null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Provider cannot be null");
        }
    }
}
