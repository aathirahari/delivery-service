package com.eatza.deliveryservice.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import com.eatza.deliveryservice.dto.OrderRequestDto;

@EnableKafka
@Configuration
public class KafkaConsumerConfiguration {
	@Value("${kafka.bootstrapAddress}")
	private String bootstrapAddress;

	@Value("${kafka.consumer.group.id}")
	private String groupId;

	@Bean
	public ConsumerFactory<String, OrderRequestDto> consumerConfig() {
		// TODO Auto-generated method stub
		Map<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		return new DefaultKafkaConsumerFactory<>(config,  new StringDeserializer(),
		            new JsonDeserializer<>(OrderRequestDto.class,false));
	}

	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, OrderRequestDto>> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, OrderRequestDto> listener = new ConcurrentKafkaListenerContainerFactory<>();
		listener.setConsumerFactory(consumerConfig());
		return listener;
	}

}