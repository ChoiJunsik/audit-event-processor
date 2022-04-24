package com.junsik.audit.processor.consumer.adapter.in;

import com.junsik.audit.processor.consumer.adapter.out.persistence.eventstore.EventStorePersistenceAdapter;
import com.junsik.audit.processor.core.domain.model.AuditEvent;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuditEventConsumer {

	private final EventStorePersistenceAdapter persistenceAdapter;

	@Transactional
	@KafkaListener(
			topics = "${audit-event-processor.topic}",
			groupId = "${audit-event-processor.consumer.group-id}",
			containerFactory = "auditBatchConsumerContainerFactory")
	public void batchListener(
			ConsumerRecords<String, AuditEvent> records, Consumer<String, Object> consumer) {

		final List<AuditEvent> auditEventList =
				StreamSupport.stream(records.spliterator(), false)
						.map(ConsumerRecord::value)
						.collect(Collectors.toList());

		persistenceAdapter.saveAll(auditEventList);

		consumer.commitSync();

		log.info("[Audit Event Consumed] {}", records);
	}
}
