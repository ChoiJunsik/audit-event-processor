package com.junsik.audit.processor.consumer.adapter.out.persistence.eventstore;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface EventStoreRepository extends JpaRepository<EventStoreEntity, Long> {}
