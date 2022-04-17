package com.junsik.chat.adapter.persistence.audit;

import com.junsik.audit.processor.producer.event.internal.InternalAuditEvent;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuditingEntityEventListener {

	private ApplicationEventPublisher applicationEventPublisher;

	public AuditingEntityEventListener(ApplicationEventPublisher applicationEventPublisher) {

		this.applicationEventPublisher = applicationEventPublisher;
	}

	public AuditingEntityEventListener() {}

	@PostUpdate
	@PostRemove
	@PostPersist
	void execute(Object entity) {
		applicationEventPublisher.publishEvent(new InternalAuditEvent(entity));
	}
}
