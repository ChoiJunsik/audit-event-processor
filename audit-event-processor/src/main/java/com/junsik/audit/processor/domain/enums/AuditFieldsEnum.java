package com.junsik.audit.processor.domain.enums;

import lombok.Getter;

@Getter
public enum AuditFieldsEnum {
	ID("id"),
	CREATE_USER("createUser"),
	UPDATE_USER("updateUser");

	private final String fieldName;

	AuditFieldsEnum(String fieldName) {
		this.fieldName = fieldName;
	}
}
