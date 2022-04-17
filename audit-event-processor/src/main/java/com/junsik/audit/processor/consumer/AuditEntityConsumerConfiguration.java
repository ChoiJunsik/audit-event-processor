package com.junsik.audit.entity.consumer;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.CooperativeStickyAssignor;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Slf4j
@ComponentScan
@Configuration
public class AuditEntityConsumerConfiguration {

	@Value("${spring.kafka.bootstrap-servers}")
	private List<String> bootstrapServers;

	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>>
			customBatchConsumerContainerFactory() {

		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		props.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, CooperativeStickyAssignor.class);

		DefaultKafkaConsumerFactory<String, Object> consumerFactory =
				new DefaultKafkaConsumerFactory<>(props);

		ConcurrentKafkaListenerContainerFactory<String, Object> listenerFactory =
				new ConcurrentKafkaListenerContainerFactory<>();

		listenerFactory
				.getContainerProperties()
				.setConsumerRebalanceListener(getConsumerRebalanceListener());

		listenerFactory.setConcurrency(1);
		listenerFactory.setBatchListener(true);
		listenerFactory.getContainerProperties().setAckMode(AckMode.MANUAL_IMMEDIATE);
		listenerFactory.setConsumerFactory(consumerFactory);
		return listenerFactory;
	}

	private ConsumerAwareRebalanceListener getConsumerRebalanceListener() {
		return new ConsumerAwareRebalanceListener() {

			@Override
			public void onPartitionsRevokedBeforeCommit(
					Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
				log.info("[rebalance occurs, when before commit] onPartitionsRevokedBeforeCommit");
			}

			// rebalance occurs, when after commit
			@Override
			public void onPartitionsRevokedAfterCommit(
					Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
				log.info("[rebalance occurs, when after commit] onPartitionsRevokedAfterCommit");
			}

			@Override
			public void onPartitionsAssigned(Collection<TopicPartition> partitions) {}

			@Override
			public void onPartitionsLost(Collection<TopicPartition> partitions) {}
		};
	}
}
