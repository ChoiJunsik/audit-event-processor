package com.junsik.chat.domain.model;

import com.junsik.chat.adapter.persistence.entity.MessageEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {
	private Long id;
	private Long receiverId;
	private String content;

	private Long createUser;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;

	public static Message of(MessageEntity messageEntity) {
		return Message.builder()
				.id(messageEntity.getId())
				.receiverId(messageEntity.getReceiverId())
				.content(messageEntity.getContent())
				.createUser(messageEntity.getCreateUser())
				.createDate(messageEntity.getCreateDate())
				.updateDate(messageEntity.getUpdateDate())
				.build();
	}
}
