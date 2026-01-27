package com.findy.post.out.repository;

import com.findy.post.app.exception.PostNotFoundException;
import com.findy.post.app.interfaces.PostRepository;
import com.findy.post.domain.model.post.Post;
import com.findy.post.out.repository.entity.PostEntity;
import com.findy.post.out.repository.jpa.JpaPostRepository;
import com.findy.post.out.repository.querydsl.PostQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {
    private final JpaPostRepository jpaPostRepository;
    private final PostQueryRepository postQueryRepository;

    @Override
    public Post save(Post post) {
        PostEntity postEntity = new PostEntity(post);
        postEntity = jpaPostRepository.save(postEntity);
        return postEntity.toPost();
    }

    @Override
    public Post findById(Long id) {
        PostEntity postEntity = jpaPostRepository
                .findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
        return postEntity.toPost();
    }

    @Override
    public void deleteById(Long id) {
        if (!jpaPostRepository.existsById(id)) {
            throw new PostNotFoundException(id);
        }
        jpaPostRepository.deleteById(id);
    }

    @Override
    public List<Post> findAll(Long cursor, int size) {
        return postQueryRepository.findAll(cursor, size)
                .stream()
                .map(PostEntity::toPost)
                .toList();
    }
}
