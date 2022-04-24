package com.junsik.sample.adapter.persistence.repository;

import com.junsik.sample.adapter.persistence.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<MessageEntity, Long> {}
