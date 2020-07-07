package com.eatza.deliveryservice.service.deliveryservice;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eatza.deliveryservice.dto.OrderRequestDto;
import com.eatza.deliveryservice.exception.DeliveryPersonException;
import com.eatza.deliveryservice.exception.InvalidOrderIdException;
import com.eatza.deliveryservice.model.DeliveryPerson;
import com.eatza.deliveryservice.model.OrderStatus;
import com.eatza.deliveryservice.repository.DeliveryPersonRepository;
import com.eatza.deliveryservice.repository.OrderStatusRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class DeliveryServiceImplTest {

	@InjectMocks
	private DeliveryServiceImpl deliveryServiceImpl;
	@Mock
	private OrderStatusRepository orderStatusRepository;
	@Mock
	private DeliveryPersonRepository deliveryPersonRepository;
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAssignDeliveryPerson() throws DeliveryPersonException {

		List<DeliveryPerson> deliveryPersons = getDeliveryPersonList();
		Mockito.when(deliveryPersonRepository.findByAvailability(true)).thenReturn(deliveryPersons);
		OrderRequestDto orderDto = createOrderDto();
		OrderStatus orderStatus = createOrderStatus();
		orderStatus.setStatus("Delivery person assigned");
		Mockito.when(orderStatusRepository.save(Mockito.any(OrderStatus.class))).thenReturn(orderStatus);
		OrderStatus result = deliveryServiceImpl.assignDeliveryPerson(orderDto);
		assertNotNull(result);
		assertEquals("Delivery person assigned", result.getStatus());
	}

	@Test
	public void testAssignDeliveryPerson_Exception() throws DeliveryPersonException {
		exception.expect(DeliveryPersonException.class);
		exception.expectMessage("Delivery Person currently unavailable,Please wait");
		// when delivery person is un-available
		OrderRequestDto orderDto = createOrderDto();
		OrderStatus orderStatus = createOrderStatus();
		orderStatus.setStatus("Delivery Person currently unavailable,Please wait");
		Mockito.when(deliveryPersonRepository.findByAvailability(true)).thenReturn(new ArrayList<>());
		deliveryServiceImpl.assignDeliveryPerson(orderDto);
	}

	@Test
	public void testCloseOrder() throws InvalidOrderIdException {
		Optional<OrderStatus> orderStatus = Optional.of(createOrderStatus());
		orderStatus.get().setStatus("delivered");
		Mockito.when(orderStatusRepository.findByOrderId(Mockito.anyLong())).thenReturn(orderStatus);
		Mockito.when(orderStatusRepository.save(Mockito.any(OrderStatus.class))).thenReturn(orderStatus.get());
		OrderStatus result = deliveryServiceImpl.closeOrder(1);
		assertNotNull(result);
		assertEquals("Delivered", result.getStatus());
	}
	
	@Test(expected=InvalidOrderIdException.class)
	public void testCloseOrder_404_IdNotFound() throws InvalidOrderIdException {
		 deliveryServiceImpl.closeOrder(1);
		
	}
	@Test(expected=InvalidOrderIdException.class)
	public void testCloseOrder_404_IdIsZero() throws InvalidOrderIdException {
		 deliveryServiceImpl.closeOrder(0);
		
	}

	private List<DeliveryPerson> getDeliveryPersonList() {
		List<DeliveryPerson> deliveryPersons = new ArrayList<>();
		DeliveryPerson deliveryPerson1 = Mockito.spy(DeliveryPerson.class);
		DeliveryPerson deliveryPerson2 = Mockito.spy(DeliveryPerson.class);
		deliveryPerson1.setId(1);
		deliveryPerson1.setName("deliveryPerson1");
		deliveryPerson1.setAvailability(false);
		deliveryPerson2.setId(1);
		deliveryPerson2.setName("deliveryPerson1");
		deliveryPerson2.setAvailability(true);
		deliveryPersons.add(deliveryPerson1);
		deliveryPersons.add(deliveryPerson2);
		return deliveryPersons;
	}

	private OrderStatus createOrderStatus() {
		OrderStatus orderStatus = Mockito.spy(OrderStatus.class);
		orderStatus.setCustomerId(new Long(1));
		orderStatus.setOrderId(new Long(1));
		orderStatus.setDeliveryPerson(new DeliveryPerson());
		return orderStatus;
	}

	private OrderRequestDto createOrderDto() {
		OrderRequestDto orderDto = Mockito.spy(OrderRequestDto.class);
		orderDto.setOrderId(new Long(1));
		orderDto.setCustomerId(new Long(1));
		return orderDto;
	}

}
