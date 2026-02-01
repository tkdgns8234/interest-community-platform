package com.findy.boundedcontext.post.in.rest.request;

public record UpdatePostRequest(
        String title,
        String content
) {
}
