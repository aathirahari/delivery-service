package com.eatza.deliveryservice.controller;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.eatza.deliveryservice.dto.OrderRequestDto;
import com.eatza.deliveryservice.exception.DeliveryPersonException;

@Service
public class KafkaConsumer {
	@Value("${kafka.topic.name}")
	private String topicName;
	@Value("${kafka.consumer.group.id}")
	private String groupId;
	@Autowired
	OrderStatusController controller;
	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
	private CountDownLatch latch = new CountDownLatch(1);

	  public CountDownLatch getLatch() {
	    return latch;
	  }
	@KafkaListener(topics = "${kafka.topic.name}", groupId = "${kafka.consumer.group.id}")
	public String receiveKafkaMessage(OrderRequestDto orderDto){
	    logger.debug("Received Messasge,group="+groupId+" topic="+topicName+" message= " + orderDto);
	    try {
	    	latch.countDown();
			controller.assignDeliveryPerson(orderDto);
		} catch (DeliveryPersonException e) {
			logger.debug(e.getMessage());
			return "delivery person un-available";
		}
	    
		return "delivery person assigned";
	    
	}
}
