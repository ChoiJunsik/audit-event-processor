package com.junsik.audit_event.consumer.config;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener;
import org.springframework.kafka.listener.ContainerProperties.AckMode;

@Configuration
public class ListenerContainerConfiguration {

	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> customBatchConsumerContainerFactory() {

		/* start, Consumer Options */
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
				List.of("localhost:9092", "localhost:9093", "localhost:9094"));
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

		DefaultKafkaConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(
				props);
		/* end, Consumer Options */

		/* start, Listener Container */
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();

		// rebalance setting
		factory.getContainerProperties().setConsumerRebalanceListener(
				new ConsumerAwareRebalanceListener() {

					// rebalance occurs, when before commit
					@Override
					public void onPartitionsRevokedBeforeCommit(Consumer<?, ?> consumer,
							Collection<TopicPartition> partitions) {
						ConsumerAwareRebalanceListener.super.onPartitionsRevokedBeforeCommit(
								consumer, partitions);
					}

					// rebalance occurs, when after commit
					@Override
					public void onPartitionsRevokedAfterCommit(Consumer<?, ?> consumer,
							Collection<TopicPartition> partitions) {
						ConsumerAwareRebalanceListener.super.onPartitionsRevokedAfterCommit(
								consumer, partitions);
					}

					@Override
					public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
						ConsumerAwareRebalanceListener.super.onPartitionsAssigned(partitions);
					}

					@Override
					public void onPartitionsLost(Collection<TopicPartition> partitions) {
						ConsumerAwareRebalanceListener.super.onPartitionsLost(partitions);
					}
				});

		factory.setConcurrency(1);
		factory.setBatchListener(true);
		factory.getContainerProperties().setAckMode(AckMode.BATCH);
		factory.setConsumerFactory(consumerFactory);
		return factory;
	}
}
