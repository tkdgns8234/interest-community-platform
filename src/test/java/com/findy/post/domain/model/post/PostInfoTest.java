package com.findy.post.domain.model.post;

import com.findy.post.domain.exception.ContentValidationException;
import com.findy.post.domain.exception.TitleValidateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PostInfoTest {

    @Nested
    @DisplayName("PostInfo 생성")
    class CreatePostInfo {

        @Test
        @DisplayName("유효한 제목과 내용으로 PostInfo를 생성할 수 있다")
        void createPostInfoWithValidTitleAndContent() {
            PostInfo postInfo = new PostInfo("테스트 제목", "테스트 내용");

            assertThat(postInfo.getTitle()).isEqualTo("테스트 제목");
            assertThat(postInfo.getContent()).isEqualTo("테스트 내용");
        }
    }

    @Nested
    @DisplayName("제목 유효성 검증")
    class TitleValidation {

        @Test
        @DisplayName("제목이 255자를 초과하면 예외가 발생한다")
        void throwExceptionWhenTitleExceedsMaxLength() {
            String longTitle = "a".repeat(256);

            assertThatThrownBy(() -> new PostInfo(longTitle, "내용"))
                    .isInstanceOf(TitleValidateException.class)
                    .hasMessage("Title length exceeds the maximum limit of 255 characters.");
        }

        @Test
        @DisplayName("제목이 빈 문자열이면 예외가 발생한다")
        void throwExceptionWhenTitleIsEmpty() {
            assertThatThrownBy(() -> new PostInfo("", "내용"))
                    .isInstanceOf(TitleValidateException.class)
                    .hasMessage("Title cannot be empty or whitespace.");
        }

        @Test
        @DisplayName("제목이 공백만 있으면 예외가 발생한다")
        void throwExceptionWhenTitleIsWhitespace() {
            assertThatThrownBy(() -> new PostInfo("   ", "내용"))
                    .isInstanceOf(TitleValidateException.class)
                    .hasMessage("Title cannot be empty or whitespace.");
        }

        @Test
        @DisplayName("제목을 255자 이하로 수정할 수 있다")
        void updateTitleWithValidLength() {
            PostInfo postInfo = new PostInfo("원래 제목", "내용");

            postInfo.setTitle("수정된 제목");

            assertThat(postInfo.getTitle()).isEqualTo("수정된 제목");
        }

        @Test
        @DisplayName("제목을 255자를 초과하도록 수정하면 예외가 발생한다")
        void throwExceptionWhenUpdatingTitleExceedsMaxLength() {
            PostInfo postInfo = new PostInfo("원래 제목", "내용");
            String longTitle = "a".repeat(256);

            assertThatThrownBy(() -> postInfo.setTitle(longTitle))
                    .isInstanceOf(TitleValidateException.class)
                    .hasMessage("Title length exceeds the maximum limit of 255 characters.");
        }
    }

    @Nested
    @DisplayName("내용 유효성 검증")
    class ContentValidation {

        @Test
        @DisplayName("내용이 10000자를 초과하면 예외가 발생한다")
        void throwExceptionWhenContentExceedsMaxLength() {
            String longContent = "a".repeat(10001);

            assertThatThrownBy(() -> new PostInfo("제목", longContent))
                    .isInstanceOf(ContentValidationException.class)
                    .hasMessage("Content length exceeds the maximum limit of 10000 characters.");
        }

        @Test
        @DisplayName("내용이 빈 문자열이어도 생성할 수 있다")
        void createPostInfoWithEmptyContent() {
            PostInfo postInfo = new PostInfo("제목", "");

            assertThat(postInfo.getContent()).isEmpty();
        }

        @Test
        @DisplayName("내용을 10000자 이하로 수정할 수 있다")
        void updateContentWithValidLength() {
            PostInfo postInfo = new PostInfo("제목", "원래 내용");

            postInfo.setContent("수정된 내용");

            assertThat(postInfo.getContent()).isEqualTo("수정된 내용");
        }

        @Test
        @DisplayName("내용을 10000자를 초과하도록 수정하면 예외가 발생한다")
        void throwExceptionWhenUpdatingContentExceedsMaxLength() {
            PostInfo postInfo = new PostInfo("제목", "원래 내용");
            String longContent = "a".repeat(10001);

            assertThatThrownBy(() -> postInfo.setContent(longContent))
                    .isInstanceOf(ContentValidationException.class)
                    .hasMessage("Content length exceeds the maximum limit of 10000 characters.");
        }
    }
}