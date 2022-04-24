package com.junsik.audit.processor.core.adapter.out.persistence.audit;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@NoArgsConstructor
public abstract class AuditEntity implements Serializable {

	@CreatedDate
	@Column(name = "create_date", nullable = false, updatable = false)
	protected LocalDateTime createDate;

	protected Long createUser = 0L;

	@LastModifiedDate
	@Column(name = "update_date", nullable = false)
	protected LocalDateTime updateDate;

	protected Long updateUser = 0L;
}
