package com.junsik.audit.processor.producer;

import com.junsik.audit.processor.vo.AuditEvent;
import com.junsik.audit.processor.vo.AuditEventType;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class AuditingEntityEventListener {

	private ApplicationEventPublisher applicationEventPublisher;
	private AuditEventFactory auditEventFactory;

	public AuditingEntityEventListener(
			ApplicationEventPublisher applicationEventPublisher, AuditEventFactory auditEventFactory) {

		this.applicationEventPublisher = applicationEventPublisher;
		this.auditEventFactory = auditEventFactory;
	}

	@PostPersist
	void onPersist(Object entity) {
		executeByType(AuditEventType.CREATED, entity);
	}

	@PostUpdate
	void onUpdate(Object entity) {
		executeByType(AuditEventType.UPDATED, entity);
	}

	@PostRemove
	void onDelete(Object entity) {
		executeByType(AuditEventType.DELETED, entity);
	}

	private void executeByType(AuditEventType type, Object entity) {
		AuditEvent auditEvent = auditEventFactory.execute(type, entity);

		applicationEventPublisher.publishEvent(auditEvent);
	}
}
