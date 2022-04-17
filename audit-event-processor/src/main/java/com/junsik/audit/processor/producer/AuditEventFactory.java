package com.junsik.audit.processor.producer;

import com.junsik.audit.processor.vo.AuditEvent;
import com.junsik.audit.processor.vo.AuditEventType;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class AuditEventFactory {

	private static final HashMap<String, AuditFieldExtractor<Object, AuditEvent>> extractorHashMap =
			new HashMap<>();

	static {
		extractorHashMap.put("id", (o, auditEvent) -> auditEvent.setEventModelId((Long) o));
		extractorHashMap.put("createUser", (o, auditEvent) -> auditEvent.setAuditUser((Long) o));
		extractorHashMap.put("updateUser", (o, auditEvent) -> auditEvent.setAuditUser((Long) o));
	}

	@Value("${audit-event-processor.service}")
	private String service;

	AuditEvent execute(AuditEventType type, Object entity) {

		AuditEvent auditEvent = getInitialAuditEvent(type, entity);

		Field[] declaredFields = entity.getClass().getDeclaredFields();

		Consumer<Field> doExtractAndFillEvent =
				field -> {
					String fieldName = getFieldName(field);

					if (this.validateBeforeExtract(fieldName, type)) {
						return;
					}

					AuditFieldExtractor<Object, AuditEvent> auditFieldExtractor =
							extractorHashMap.get(fieldName);

					if (Objects.isNull(auditFieldExtractor)) {
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

	private String getFieldName(Field field) {
		field.setAccessible(true);
		return field.getName();
	}

	private boolean validateBeforeExtract(String fieldName, AuditEventType type) {
		return type == AuditEventType.UPDATED && "createUser".equals(fieldName);
	}

	private AuditEvent getInitialAuditEvent(AuditEventType type, Object entity) {
		return AuditEvent.builder()
				.eventTime(LocalDateTime.now())
				.eventModel(entity.getClass().getSimpleName())
				.service(service)
				.eventType(type)
				.payload(entity)
				.build();
	}
}
