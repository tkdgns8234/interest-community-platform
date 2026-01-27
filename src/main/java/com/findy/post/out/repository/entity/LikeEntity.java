package com.findy.post.out.repository.entity;

import com.findy.common.out.repository.entity.BaseTimeEntity;
import com.findy.post.domain.model.like.Like;
import com.findy.post.domain.model.like.TargetType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "likes", indexes = {
        @Index(name = "idx_user_target", columnList = "userId,targetId,targetType"),
        @Index(name = "idx_target", columnList = "targetId,targetType")
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LikeEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long targetId;

    @Enumerated(EnumType.STRING)
    private TargetType targetType;

    public LikeEntity(Like like) {
        this.id = like.getId();
        this.userId = like.getUserId();
        this.targetId = like.getTargetId();
        this.targetType = like.getTargetType();
    }

    public Like toLike() {
        return new Like(id, userId, targetId, targetType);
    }
}