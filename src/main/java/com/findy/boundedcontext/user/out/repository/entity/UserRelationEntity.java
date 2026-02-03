package com.findy.boundedcontext.user.out.repository.entity;

import com.findy.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(
    name = "user_relation",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_user_relation_user_id_target_user_id",
            columnNames = {"user_id", "target_user_id"}
        )
    },
    indexes = {
        @Index(name = "idx_user_relation_target_user_id", columnList = "target_user_id")
    }
)
public class UserRelationEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long targetUserId;
}
