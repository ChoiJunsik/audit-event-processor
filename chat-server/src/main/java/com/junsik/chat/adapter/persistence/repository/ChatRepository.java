package com.junsik.chat.adapter.persistence.repository;

import com.junsik.chat.adapter.persistence.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<MessageEntity, Long> {}
