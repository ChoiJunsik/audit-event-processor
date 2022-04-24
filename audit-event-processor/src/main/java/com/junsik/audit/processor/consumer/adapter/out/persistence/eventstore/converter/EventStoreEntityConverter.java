package com.junsik.audit.processor.consumer.adapter.out.persistence.eventstore.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junsik.audit.processor.consumer.adapter.out.persistence.eventstore.EventStoreEntity;
import com.junsik.audit.processor.core.domain.model.AuditEvent;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class EventStoreEntityConverter {

	private final ObjectMapper objectMapper;

	public EventStoreEntity to(final AuditEvent event) {
		return EventStoreEntity.builder()
				.service(event.getService())
				.eventTime(event.getEventTime())
				.eventUser(event.getEventUser())
				.eventType(event.getEventType())
				.publishType(event.getPublishType())
				.domain(event.getDomain())
				.domainId(event.getDomainId())
				.payload(this.toJsonString(event.getPayload()))
				.build();
	}

	public AuditEvent from(final EventStoreEntity entity) {
		return AuditEvent.builder()
				.service(entity.getService())
				.eventTime(entity.getEventTime())
				.eventUser(entity.getEventUser())
				.eventType(entity.getEventType())
				.publishType(entity.getPublishType())
				.domain(entity.getDomain())
				.domainId(entity.getDomainId())
				.payload(this.toPayload(entity.getPayload()))
				.build();
	}

	private Object toPayload(final String json) {
		return objectMapper.convertValue(json, Object.class);
	}

	private String toJsonString(final Object payload) {
		try {
			return objectMapper.writeValueAsString(payload);
		} catch (IOException e) {
			log.error("[Fail:AuditEvent's Payload to Json String]", e);
		}

		return null;
	}
}
