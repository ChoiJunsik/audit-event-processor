package com.junsik.chat.adapter.consumer.internal.event;

import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Slf4j
@Component
public class InternalAuditEventPublisher {

	private static final String TOPIC_NAME = "audit-chat-event";
	private static final String MESSAGE_KEY = "chat-server";

	private final KafkaTemplate<String, Object> kafkaTemplate;

	public InternalAuditEventPublisher(
			@Qualifier("auditEventKafkaTemplate") KafkaTemplate<String, Object> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@Async
	@Transactional
	@EventListener
	public void onAuditEvent(final AuditEvent event) {

		ListenableFuture<SendResult<String, Object>> future =
				kafkaTemplate.send(TOPIC_NAME, MESSAGE_KEY, event.getSource());
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
