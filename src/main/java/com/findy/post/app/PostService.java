package com.findy.post.app;

import com.findy.post.app.dto.CreatePostCommand;
import com.findy.post.app.dto.UpdatePostCommand;
import com.findy.post.app.interfaces.PostRepository;
import com.findy.post.domain.model.post.Post;
import com.findy.post.domain.model.post.PostInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Post createPost(CreatePostCommand command) {
        PostInfo postInfo = new PostInfo(command.title(), command.content());
        Post post = new Post(null, command.authorId(), postInfo);
        return postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public Post getPost(Long id) {
        return postRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Post> getAllPosts(Long cursor, int size) {
        return postRepository.findAll(cursor, size);
    }

    @Transactional
    public Post updatePost(UpdatePostCommand command) {
        Post post = postRepository.findById(command.id());
        post.setTitle(command.title());
        post.setContent(command.content());
        return postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
