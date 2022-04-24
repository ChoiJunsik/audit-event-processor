package com.junsik.audit.processor.consumer.port.out;

import com.junsik.audit.processor.core.domain.model.AuditEvent;
import java.util.Collection;
import java.util.List;

public interface EventStoreDataProvider {
	List<AuditEvent> saveAll(final Collection<AuditEvent> events);
}
