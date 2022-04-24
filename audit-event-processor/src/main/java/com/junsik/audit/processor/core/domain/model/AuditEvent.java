package com.junsik.audit.processor.core.domain.model;

import com.junsik.audit.processor.core.domain.enums.AuditEventType;
import com.junsik.audit.processor.core.domain.enums.EventPublishType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditEvent {
	private Long id;
	private String service;

	private LocalDateTime eventTime;
	private Long eventUser;
	private AuditEventType eventType;
	private EventPublishType publishType;

	private String domain;
	private Long domainId;
	private Object payload;

	public void setEventUser(final Long eventUser) {
		this.eventUser = eventUser;
	}

	public void setDomainId(final Long domainId) {
		this.domainId = domainId;
	}
}
