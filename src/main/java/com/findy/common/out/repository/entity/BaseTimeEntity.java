package com.findy.common.out.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@MappedSuperclass
public class BaseTimeEntity {
    @CreatedDate
    @Column(updatable = false)
    public LocalDateTime createdAt;

    @LastModifiedDate
    public LocalDateTime updatedAt;
}
