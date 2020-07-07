package com.eatza.deliveryservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.eatza.deliveryservice.dto.OrderRequestDto;
import com.eatza.deliveryservice.exception.DeliveryPersonException;
import com.eatza.deliveryservice.exception.InvalidOrderIdException;
import com.eatza.deliveryservice.model.OrderStatus;
import com.eatza.deliveryservice.service.deliveryservice.DeliveryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class OrderStatusController {

	@Autowired
	DeliveryService deliveryService;
	
	private static final Logger logger = LoggerFactory.getLogger(OrderStatusController.class);
	@Value("${kafka.topic.name}")
	private String topicName;
	@Value("${kafka.consumer.group.id}")
	private String groupId;
	
	
	//@KafkaListener(topics = "${kafka.topic.name}", groupId = "${kafka.consumer.group.id}")
	@PostMapping("/delivery")
	public ResponseEntity<String> assignDeliveryPerson(@RequestBody OrderRequestDto orderDto) throws DeliveryPersonException{
		logger.debug("In assignDeliveryPerson method,calling delivery order service");
		OrderStatus orderStatus= deliveryService.assignDeliveryPerson(orderDto);
		logger.debug("Order saved Successfully and delivery person assigned");
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(orderStatus.getStatus());
	}
	@PostMapping("/delivery/closeOrder/{id}")
	public ResponseEntity<String> closeOrder(@RequestHeader String authorization, @PathVariable int id) throws DeliveryPersonException, InvalidOrderIdException{
		logger.debug("In closeOrder method,calling delivery order service");
		OrderStatus orderStatus= deliveryService.closeOrder(id);
		logger.debug("Order closed Successfully");
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(orderStatus.getStatus());
	}
	
}
