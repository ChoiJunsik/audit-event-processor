package com.junsik.sample.port;

import com.junsik.sample.domain.command.CreateMessageCommand;
import com.junsik.sample.domain.model.Message;

public interface CreateMessageUseCase {
	Message execute(final CreateMessageCommand command);
}
