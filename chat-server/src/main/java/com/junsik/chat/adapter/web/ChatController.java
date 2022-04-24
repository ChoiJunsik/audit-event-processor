package com.junsik.chat.adapter.web;

import com.junsik.chat.adapter.web.request.SendMessageRequest;
import com.junsik.chat.domain.model.Message;
import com.junsik.chat.port.CreateMessageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
@RestController
public class ChatController {

	private final CreateMessageUseCase createMessageUseCase;

	@PostMapping
	public Message add(@RequestBody final SendMessageRequest request) {
		return createMessageUseCase.execute(request.toCommand());
	}
}
