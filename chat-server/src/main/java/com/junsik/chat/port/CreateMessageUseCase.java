package com.junsik.chat.port;

import com.junsik.chat.domain.command.CreateMessageCommand;
import com.junsik.chat.domain.model.Message;

public interface CreateMessageUseCase {
	Message execute(final CreateMessageCommand command);
}
