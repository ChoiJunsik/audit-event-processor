package com.junsik.chat.domain.service;

import com.junsik.chat.adapter.persistence.entity.MessageEntity;
import com.junsik.chat.adapter.persistence.repository.ChatRepository;
import com.junsik.chat.domain.command.CreateMessageCommand;
import com.junsik.chat.domain.model.Message;
import com.junsik.chat.port.CreateMessageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CreateMessageService implements CreateMessageUseCase {

	private final ChatRepository chatRepository;

	@Override
	public Message execute(CreateMessageCommand command) {
		MessageEntity messageEntity =
				MessageEntity.builder()
						.receiverId(command.getReceiverId())
						.createUser(command.getSenderId())
						.content(command.getContent())
						.build();

		return Message.of(chatRepository.save(messageEntity));
	}
}
