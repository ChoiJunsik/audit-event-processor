package com.junsik.chat.adapter.controller.request;

import com.junsik.chat.domain.command.CreateMessageCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SendMessageRequest {
	private Long senderId;
	private Long receiverId;
	private String content;

	public CreateMessageCommand to() {
		return CreateMessageCommand.builder()
				.senderId(this.senderId)
				.receiverId(this.receiverId)
				.content(this.content)
				.build();
	}
}
