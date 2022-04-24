package com.junsik.chat.adapter.persistence.entity;

import com.junsik.audit.processor.adapter.out.audit.AuditEntity;
import com.junsik.audit.processor.adapter.out.persistence.AuditingEntityEventListener;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
@EntityListeners({AuditingEntityEventListener.class})
@Table(name = "message")
@Entity
public class MessageEntity extends AuditEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long receiverId;
	private String content;
}
