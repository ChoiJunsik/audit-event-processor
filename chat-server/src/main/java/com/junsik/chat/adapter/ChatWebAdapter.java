package com.junsik.chat.adapter;

import com.junsik.chat.adapter.controller.request.SendMessageRequest;
import com.junsik.chat.domain.model.Message;
import com.junsik.chat.port.CreateMessageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ChatWebAdapter {
	private final CreateMessageUseCase createMessageUseCase;

	public Message add(final SendMessageRequest request) {
		return createMessageUseCase.execute(request.to());
	}
}
