package com.devsync.gitservice.repository;

import com.devsync.gitservice.entity.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OutboxRepository extends JpaRepository<Outbox, UUID> {
    List<Outbox> findByPublishedFalse();
}
