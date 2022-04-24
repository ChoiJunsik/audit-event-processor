package com.junsik.audit.processor.core.domain.utils;

@FunctionalInterface
public interface AuditFieldExtractor<T, E> {
	void execute(T t, E e);
}
