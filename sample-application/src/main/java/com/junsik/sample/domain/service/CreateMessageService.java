package com.junsik.sample.domain.service;

import com.junsik.sample.adapter.persistence.entity.MessageEntity;
import com.junsik.sample.adapter.persistence.repository.ChatRepository;
import com.junsik.sample.domain.command.CreateMessageCommand;
import com.junsik.sample.domain.model.Message;
import com.junsik.sample.port.CreateMessageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
class CreateMessageService implements CreateMessageUseCase {

	private final ChatRepository chatRepository;

	@Override
	public Message execute(CreateMessageCommand command) {
		MessageEntity messageEntity =
				MessageEntity.builder()
						.receiverId(command.getReceiverId())
						.content(command.getContent())
						.build();

		return Message.of(chatRepository.save(messageEntity));
	}
}
