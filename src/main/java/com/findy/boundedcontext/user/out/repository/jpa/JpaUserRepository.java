package com.findy.boundedcontext.user.out.repository.jpa;

import com.findy.boundedcontext.user.out.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
}
