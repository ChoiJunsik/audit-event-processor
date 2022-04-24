package com.junsik.audit.processor.producer.adapter.out;

import com.junsik.audit.processor.core.domain.model.AuditEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.util.concurrent.ListenableFuture;

@Slf4j
@Component
public class InternalAuditEventPublisher {

	@Value("${audit-event-processor.topic}")
	private String TOPIC_NAME;

	@Value("${audit-event-processor.message-key}")
	private String MESSAGE_KEY;

	private final KafkaTemplate<String, Object> kafkaTemplate;

	public InternalAuditEventPublisher(
			@Qualifier("auditEventKafkaTemplate") KafkaTemplate<String, Object> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@Async
	@Transactional
	@TransactionalEventListener(fallbackExecution = true)
	public void onAuditEvent(final AuditEvent event) {

		final ListenableFuture<SendResult<String, Object>> future =
				kafkaTemplate.send(TOPIC_NAME, MESSAGE_KEY, event);

		future.addCallback(
				new KafkaSendCallback<>() {

					@Override
					public void onSuccess(SendResult<String, Object> result) {
						log.info("audit event success {}", result);
					}

					@Override
					public void onFailure(KafkaProducerException ex) {
						log.error("audit event fail", ex);
					}
				});
	}
}
