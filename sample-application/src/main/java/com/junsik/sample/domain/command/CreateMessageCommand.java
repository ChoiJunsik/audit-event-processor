package com.junsik.sample.domain.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateMessageCommand {
	private Long senderId;
	private Long receiverId;
	private String content;
}
