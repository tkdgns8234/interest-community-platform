package com.findy.post.out.repository.entity;

import com.findy.common.out.repository.entity.BaseTimeEntity;
import com.findy.post.domain.model.LikeManager;
import com.findy.post.domain.model.post.Post;
import com.findy.post.domain.model.post.PostInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long authorId;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Long likeCount;

    public PostEntity(Post post) {
        this.id = post.getId();
        this.authorId = post.getAuthorId();
        this.title = post.getPostInfo().getTitle();
        this.content = post.getPostInfo().getContent();
        this.likeCount = post.getLikeManager().getCount();
    }

    public Post toPost() {
        return new Post(id, authorId, new PostInfo(title, content), new LikeManager(likeCount));
    }
}