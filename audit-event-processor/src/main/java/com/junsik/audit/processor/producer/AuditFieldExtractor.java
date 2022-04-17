package com.junsik.audit.processor.producer;

@FunctionalInterface
public interface AuditFieldExtractor<T, E> {

	void execute(T t, E e);
}
