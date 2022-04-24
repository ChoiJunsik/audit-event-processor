package com.junsik.sample.adapter.web.request;

import com.junsik.sample.domain.command.CreateMessageCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SendMessageRequest {
	private Long senderId;
	private Long receiverId;
	private String content;

	public CreateMessageCommand toCommand() {
		return CreateMessageCommand.builder()
				.senderId(this.senderId)
				.receiverId(this.receiverId)
				.content(this.content)
				.build();
	}
}
