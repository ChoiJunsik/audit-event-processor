package com.junsik.chat.adapter.controller;

import com.junsik.chat.adapter.ChatWebAdapter;
import com.junsik.chat.adapter.controller.request.SendMessageRequest;
import com.junsik.chat.domain.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
@RestController
public class ChatController {

	private final ChatWebAdapter adapter;

	@PostMapping
	public Message add(@RequestBody final SendMessageRequest request) {
		return adapter.add(request);
	}
}
