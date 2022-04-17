package com.junsik.audit.entity.producer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.UniformStickyPartitioner;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@ComponentScan
@Configuration
public class AuditEntityProducerConfiguration {

	private static final String TRANSACTIONAL_ID = "AUDIT_ENTITY";

	@Value("${spring.kafka.bootstrap-servers}")
	private List<String> bootstrapServers;

	@Bean(name = "auditEventKafkaTemplate")
	public KafkaTemplate<String, Object> auditEventKafkaTemplate() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

		props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, UniformStickyPartitioner.class);

		props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
		props.put(ProducerConfig.ACKS_CONFIG, "all");
		props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, TRANSACTIONAL_ID);

		ProducerFactory<String, Object> producerFactory = new DefaultKafkaProducerFactory<>(props);

		return new KafkaTemplate<>(producerFactory);
	}
}
