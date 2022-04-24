package com.junsik.audit.processor.domain;

@FunctionalInterface
public interface AuditFieldExtractor<T, E> {
	void execute(T t, E e);
}
