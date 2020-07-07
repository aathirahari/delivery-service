package com.eatza.deliveryservice.service.deliveryservice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.eatza.deliveryservice.dto.OrderRequestDto;
import com.eatza.deliveryservice.exception.DeliveryPersonException;
import com.eatza.deliveryservice.exception.InvalidOrderIdException;
import com.eatza.deliveryservice.model.OrderStatus;
import com.eatza.deliveryservice.model.DeliveryPerson;
import com.eatza.deliveryservice.repository.OrderStatusRepository;
import com.eatza.deliveryservice.repository.DeliveryPersonRepository;
@Service
public class DeliveryServiceImpl implements DeliveryService{

	@Autowired
	private OrderStatusRepository orderStatusRepository;
	@Autowired
	private DeliveryPersonRepository deliveryPersonRepository;
	@Override
	public OrderStatus assignDeliveryPerson(OrderRequestDto orderDto) throws DeliveryPersonException {
		OrderStatus savedOrder=null;
		List<DeliveryPerson> deliveryPersons=deliveryPersonRepository.findByAvailability(true);
		if(!deliveryPersons.isEmpty())
		{
		DeliveryPerson deliveryPerson=deliveryPersons.get(0);
		deliveryPerson.setAvailability(false);
		deliveryPersonRepository.save(deliveryPerson);
		OrderStatus orderStatus= new OrderStatus( orderDto.getOrderId(), orderDto.getCustomerId(), "Delivery person assigned", deliveryPersons.get(0));
		savedOrder=orderStatusRepository.save(orderStatus);
		}
		else
		{
			OrderStatus orderStatus= new OrderStatus( orderDto.getOrderId(), orderDto.getCustomerId(), "Delivery person pending", null);
			savedOrder=orderStatusRepository.save(orderStatus);
			throw new DeliveryPersonException("Delivery Person currently unavailable,Please wait");
		}
		return savedOrder;
	}
	@Override
	public OrderStatus closeOrder(int id) throws InvalidOrderIdException {
		if(id==0)
			throw new InvalidOrderIdException("Order id cant be 0");
		Optional<OrderStatus> order=orderStatusRepository.findByOrderId(new Long(id));
		order.orElseThrow(()-> new InvalidOrderIdException("Order with id:"+id+" not found"));
		OrderStatus orderStatus=order.get();
		DeliveryPerson deliveryPerson=orderStatus.getDeliveryPerson();
		deliveryPerson.setAvailability(false);
		deliveryPersonRepository.save(deliveryPerson);
		orderStatus.setStatus("Delivered");
		return  orderStatusRepository.save(orderStatus);
		
	}
	

}
