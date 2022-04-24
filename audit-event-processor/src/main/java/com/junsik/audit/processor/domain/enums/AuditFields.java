package com.junsik.audit.processor.domain.enums;

import lombok.Getter;

@Getter
public enum AuditFields {
	ID("id"),
	CREATE_USER("createUser"),
	UPDATE_USER("updateUser");

	private final String field;

	AuditFields(String field) {
		this.field = field;
	}
}
