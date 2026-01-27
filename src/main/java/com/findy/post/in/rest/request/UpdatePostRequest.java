package com.findy.post.in.rest.request;

public record UpdatePostRequest(
        String title,
        String content
) {
}
