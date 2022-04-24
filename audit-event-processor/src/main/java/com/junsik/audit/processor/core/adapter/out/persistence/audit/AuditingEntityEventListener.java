package com.junsik.audit.processor.core.adapter.out.persistence.audit;

import com.junsik.audit.processor.core.domain.enums.AuditEventType;
import com.junsik.audit.processor.core.domain.model.AuditEvent;
import com.junsik.audit.processor.core.domain.utils.AuditEventFactory;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuditingEntityEventListener {

	private final ApplicationEventPublisher applicationEventPublisher;
	private final AuditEventFactory auditEventFactory;

	@PostPersist
	void onPersist(final Object entity) {
		executeByType(AuditEventType.CREATED, entity);
	}

	@PostUpdate
	void onUpdate(final Object entity) {
		executeByType(AuditEventType.UPDATED, entity);
	}

	@PostRemove
	void onDelete(final Object entity) {
		executeByType(AuditEventType.DELETED, entity);
	}

	private void executeByType(final AuditEventType type, final Object entity) {
		final AuditEvent auditEvent = auditEventFactory.build(type, entity);

		applicationEventPublisher.publishEvent(auditEvent);
	}
}
