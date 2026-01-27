package com.findy.post.app.interfaces;

import com.findy.post.domain.model.post.Post;

import java.util.List;

public interface PostRepository {
    Post save(Post post);
    Post findById(Long id);
    void deleteById(Long id);
    List<Post> findAll(Long cursor, int size);
}
