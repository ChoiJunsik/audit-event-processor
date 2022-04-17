package com.junsik.audit.processor.vo;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditEvent {
	private LocalDateTime eventTime;
	private String service;
	private Long auditUser;
	private AuditEventType eventType;
	private String eventModel;
	private Long eventModelId;
	private Object payload;
}
