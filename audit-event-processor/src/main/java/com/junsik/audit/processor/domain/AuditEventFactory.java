package com.junsik.audit.processor.domain;

import com.junsik.audit.processor.domain.enums.AuditEventType;
import com.junsik.audit.processor.domain.enums.AuditFields;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuditEventFactory {

	private static final HashMap<String, AuditFieldExtractor<Object, AuditEvent>> extractorHashMap = new HashMap<>();

	static {
		extractorHashMap.put(AuditFields.ID.getField(),
				(o, auditEvent) -> auditEvent.setDomainId((Long) o));
		extractorHashMap.put(AuditFields.CREATE_USER.getField(),
				(o, auditEvent) -> auditEvent.setEventUser((Long) o));
		extractorHashMap.put(AuditFields.UPDATE_USER.getField(),
				(o, auditEvent) -> auditEvent.setEventUser((Long) o));
	}

	@Value("${audit-event-processor.service}")
	private String service;

	public AuditEvent build(final AuditEventType type, final Object entity) {

		final AuditEvent auditEvent = getInitialAuditEvent(type, entity);

		final Field[] declaredFields = entity.getClass().getDeclaredFields();

		Consumer<Field> doExtractAndFillEvent = field -> {
			field.setAccessible(true);

			final String fieldName = field.getName();

			if (this.validateBeforeExtract(fieldName, type)) {
				return;
			}

			final AuditFieldExtractor<Object, AuditEvent> auditFieldExtractor = extractorHashMap.get(fieldName);

			if (auditFieldExtractor == null) {
				return;
			}

			try {
				auditFieldExtractor.execute(field.get(entity), auditEvent);
			} catch (IllegalAccessException exception) {
				log.error("Audit Entity Reflection Exception", exception);
			}
		};

		Arrays.stream(declaredFields).forEach(doExtractAndFillEvent);

		return auditEvent;
	}

	private boolean validateBeforeExtract(String fieldName, AuditEventType type) {
		return type == AuditEventType.UPDATED && AuditFields.CREATE_USER.getField()
				.equals(fieldName);
	}

	private AuditEvent getInitialAuditEvent(AuditEventType type, Object entity) {
		return AuditEvent.builder().eventTime(LocalDateTime.now())
				.domain(entity.getClass().getSimpleName()).service(service).eventType(type)
				.payload(entity).build();
	}
}
