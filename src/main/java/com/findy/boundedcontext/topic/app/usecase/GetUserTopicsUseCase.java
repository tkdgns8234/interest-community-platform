package com.findy.boundedcontext.topic.app.usecase;

import com.findy.boundedcontext.topic.app.interfaces.TopicMembershipRepository;
import com.findy.boundedcontext.topic.domain.model.membership.TopicMembership;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetUserTopicsUseCase {
    private final TopicMembershipRepository membershipRepository;

    @Transactional(readOnly = true)
    public List<TopicMembership> execute(Long userId, Long cursor, int size) {
        return membershipRepository.findByUserId(userId, cursor, size);
    }
}
