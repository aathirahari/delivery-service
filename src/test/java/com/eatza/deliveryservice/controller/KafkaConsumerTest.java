package com.eatza.deliveryservice.controller;

import static org.junit.Assert.*;

import static org.junit.Assert.assertThat;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.junit4.SpringRunner;

import com.eatza.deliveryservice.dto.OrderRequestDto;


@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaConsumerTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerTest.class);
	  static String RECEIVER_TOPIC = "orderData";
//
//////	  @Autowired
//////	  private Receiver receiver;
@Autowired
private KafkaConsumer consumer;
	  private KafkaTemplate<String, OrderRequestDto> template;

	  @Autowired
	  private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

	  @ClassRule
	  public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, RECEIVER_TOPIC);

	  @Before
	  public void setUp() throws Exception {
	    // set up the Kafka producer properties
	    Map<String, Object> senderProperties =
	        KafkaTestUtils.senderProps(embeddedKafka.getBrokersAsString());
	    
	    Map<String, Object> producerProps = KafkaTestUtils.producerProps(embeddedKafka);

	    // create a Kafka producer factory
	    ProducerFactory<String, OrderRequestDto> producerFactory =
	       // new DefaultKafkaProducerFactory<String, OrderRequestDto>(producerProps);
new DefaultKafkaProducerFactory<>(senderProperties, new StringSerializer(), new JsonSerializer<>());
	    // create a Kafka template
	    template = new KafkaTemplate<>(producerFactory);
	    // set the default topic to send to
	    template.setDefaultTopic(RECEIVER_TOPIC);
	   

	    // wait until the partitions are assigned
//	    for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry
//	        .getListenerContainers()) {
//	      ContainerTestUtils.waitForAssignment(messageListenerContainer,
//	          embeddedKafka.getPartitionsPerTopic());
//	    }
	  }

	  @Test
	  public void testReceive() throws Exception {
	    // send the message
		  OrderRequestDto orderRequestDto = new OrderRequestDto(new Long(2), new
					 Long(1));
		 
	    template.send(RECEIVER_TOPIC,orderRequestDto);
	    
	    LOGGER.debug("test-sender sent message='{}'", orderRequestDto);
	    
	    consumer.getLatch().await(10000, TimeUnit.MILLISECONDS);
	    // check that the message was received
	  assertEquals(consumer.getLatch().getCount(),1);
	  }
	  
}
