package com.eatza.deliveryservice.service.deliveryservice;

import com.eatza.deliveryservice.dto.OrderRequestDto;
import com.eatza.deliveryservice.exception.DeliveryPersonException;
import com.eatza.deliveryservice.exception.InvalidOrderIdException;
import com.eatza.deliveryservice.model.OrderStatus;

public interface DeliveryService {

	OrderStatus assignDeliveryPerson(OrderRequestDto orderDto) throws DeliveryPersonException;

	OrderStatus closeOrder(int id) throws InvalidOrderIdException;

}
