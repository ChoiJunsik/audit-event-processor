package com.junsik.audit.processor.consumer.adapter.out.persistence.eventstore;

import com.junsik.audit.processor.consumer.adapter.out.persistence.eventstore.converter.EventStoreEntityConverter;
import com.junsik.audit.processor.consumer.port.out.EventStoreDataProvider;
import com.junsik.audit.processor.core.domain.model.AuditEvent;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EventStorePersistenceAdapter implements EventStoreDataProvider {

	private final EventStoreRepository eventStoreRepository;

	private final EventStoreEntityConverter converter;

	@Override
	public List<AuditEvent> saveAll(final Collection<AuditEvent> events) {
		final List<EventStoreEntity> eventStoreEntityList =
				events.stream().map(converter::to).collect(Collectors.toList());

		final List<EventStoreEntity> savedEventStoreEntityList =
				eventStoreRepository.saveAll(eventStoreEntityList);

		return savedEventStoreEntityList.stream().map(converter::from).collect(Collectors.toList());
	}
}
