package com.junsik.chat.adapter.consumer.internal.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuditEvent {
	private Object source;
}
