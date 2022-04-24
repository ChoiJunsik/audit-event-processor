package com.junsik.audit.processor.consumer.adapter.out.persistence.eventstore;

import com.junsik.audit.processor.core.domain.enums.AuditEventType;
import com.junsik.audit.processor.core.domain.enums.EventPublishType;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "event_store")
@Entity
public class EventStoreEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String service;

	private LocalDateTime eventTime;
	private Long eventUser;

	@Enumerated(value = EnumType.STRING)
	private AuditEventType eventType;

	@Enumerated(value = EnumType.STRING)
	private EventPublishType publishType;

	private String domain;
	private Long domainId;

	@Column(columnDefinition = "JSON")
	private String payload;
}
