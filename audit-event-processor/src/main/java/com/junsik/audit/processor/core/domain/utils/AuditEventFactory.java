package com.junsik.audit.processor.core.domain.utils;

import com.junsik.audit.processor.core.domain.enums.AuditEventType;
import com.junsik.audit.processor.core.domain.enums.AuditFieldsEnum;
import com.junsik.audit.processor.core.domain.enums.EventPublishType;
import com.junsik.audit.processor.core.domain.model.AuditEvent;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuditEventFactory {

	private static final HashMap<String, AuditFieldExtractor<Object, AuditEvent>> extractorHashMap =
			new HashMap<>();

	@Getter
	@Builder
	@AllArgsConstructor
	private static class ExtractorParams {
		private Field field;
		private AuditEvent auditEvent;
		private AuditEventType type;
		private Object entity;
	}

	static {
		extractorHashMap.put(
				AuditFieldsEnum.ID.getFieldName(), (o, auditEvent) -> auditEvent.setDomainId((Long) o));
		extractorHashMap.put(
				AuditFieldsEnum.CREATE_USER.getFieldName(),
				(o, auditEvent) -> auditEvent.setEventUser((Long) o));
		extractorHashMap.put(
				AuditFieldsEnum.UPDATE_USER.getFieldName(),
				(o, auditEvent) -> auditEvent.setEventUser((Long) o));
	}

	@Value("${audit-event-processor.service}")
	private String service;

	public AuditEvent build(final AuditEventType type, final Object entity) {

		final AuditEvent auditEvent = getInitialAuditEvent(type, entity);

		final Field[] declaredFields = entity.getClass().getDeclaredFields();

		Arrays.stream(declaredFields)
				.forEach(
						field ->
								this.doExtractAndFillEvent(
										ExtractorParams.builder()
												.auditEvent(auditEvent)
												.entity(entity)
												.field(field)
												.type(type)
												.build()));

		return auditEvent;
	}

	private void doExtractAndFillEvent(final ExtractorParams params) {
		final Field field = params.getField();

		field.setAccessible(true);

		final String fieldName = field.getName();

		if (this.validateBy(fieldName, params.getType())) {
			return;
		}

		final AuditFieldExtractor<Object, AuditEvent> auditFieldExtractor =
				extractorHashMap.get(fieldName);

		if (auditFieldExtractor == null) {
			return;
		}

		try {
			auditFieldExtractor.execute(field.get(params.getEntity()), params.getAuditEvent());
		} catch (IllegalAccessException exception) {
			log.error("Audit Entity Reflection Exception", exception);
		}
	}

	private boolean validateBy(final String fieldName, final AuditEventType type) {
		return type == AuditEventType.UPDATED
				&& AuditFieldsEnum.CREATE_USER.getFieldName().equals(fieldName);
	}

	private AuditEvent getInitialAuditEvent(final AuditEventType type, final Object entity) {
		return AuditEvent.builder()
				.eventTime(LocalDateTime.now())
				.eventType(type)
				.publishType(EventPublishType.IMMEDIATELY)
				.domain(entity.getClass().getSimpleName())
				.service(service)
				.payload(entity)
				.build();
	}
}
