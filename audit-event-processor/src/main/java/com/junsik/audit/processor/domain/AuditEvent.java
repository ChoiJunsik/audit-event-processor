package com.junsik.audit.processor.domain;

import com.junsik.audit.processor.domain.enums.AuditEventType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
