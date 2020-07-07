package com.eatza.deliveryservice.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.eatza.deliveryservice.dto.OrderRequestDto;
import com.eatza.deliveryservice.exception.DeliveryPersonException;
import com.eatza.deliveryservice.exception.InvalidOrderIdException;
import com.eatza.deliveryservice.exception.InvalidTokenException;
import com.eatza.deliveryservice.model.OrderStatus;
import com.eatza.deliveryservice.service.deliveryservice.DeliveryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = OrderStatusController.class)
public class OrderStatusControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired 
	private  OrderStatusController controller;
	@MockBean
	private DeliveryService deliveryService;

	@Autowired
	private ObjectMapper objectMapper;

	String jwt = "";
	private static final long EXPIRATIONTIME = 900000;
	OrderStatus orderStatus = Mockito.spy(OrderStatus.class);
	@Before
	public void setup() {
		jwt = "Bearer " + Jwts.builder().setSubject("user").claim("roles", "user").setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "secretkey")
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME)).compact();
	}
	@After
	public void reset_mocks() {
	    Mockito.reset(orderStatus);
	}

	@Test
	public void testAssignDeliveryPerson() throws Exception {
		OrderRequestDto orderRequestDto = new OrderRequestDto();
		
		orderStatus.setStatus("Delivery person assigned");
		Mockito.when(deliveryService.assignDeliveryPerson(Mockito.any(OrderRequestDto.class))).thenReturn(orderStatus);
		RequestBuilder request = MockMvcRequestBuilders.post("/delivery").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString((orderRequestDto)));
		// when delivery person is available
		mockMvc.perform(request).andExpect(status().is(200)).andExpect(content().string("Delivery person assigned"))
				.andReturn();
		
	}

	@Test
	public void testAssignDeliveryPerson_Exception() throws Exception {
		// when delivery person is un-available
		OrderRequestDto orderRequestDto = new OrderRequestDto();
		Mockito.when(deliveryService.assignDeliveryPerson(Mockito.any(OrderRequestDto.class)))
				.thenThrow(new DeliveryPersonException("Delivery Person currently unavailable,Please wait"));
		RequestBuilder request = MockMvcRequestBuilders.post("/delivery").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString((orderRequestDto)));
		mockMvc.perform(request).andExpect(status().is(200))
				.andExpect(content().string("Delivery Person currently unavailable,Please wait")).andReturn();
	}

	@Test
	public void testCloseOrder() throws Exception {
		OrderRequestDto orderRequestDto = new OrderRequestDto();
		orderStatus.setStatus("Delivered");
		Mockito.when(deliveryService.closeOrder(Mockito.anyInt())).thenReturn(orderStatus);
		RequestBuilder request = MockMvcRequestBuilders.post("/delivery/closeOrder/1")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString((orderRequestDto)))
				.header(HttpHeaders.AUTHORIZATION, jwt);
		mockMvc.perform(request).andExpect(status().is(200)).andExpect(content().string("Delivered")).andReturn();
	}
	@Test
	public void testCloseOrder_Exception() throws Exception {
		OrderRequestDto orderRequestDto = new OrderRequestDto();
		Mockito.when(deliveryService.closeOrder(Mockito.anyInt())).thenThrow(InvalidOrderIdException.class);
		RequestBuilder request = MockMvcRequestBuilders.post("/delivery/closeOrder/1")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString((orderRequestDto)))
				.header(HttpHeaders.AUTHORIZATION, jwt);
		mockMvc.perform(request).andExpect(status().is(404)).andReturn();
	}

	@Test(expected=InvalidTokenException.class)
	public void testCloseOrder_UnAuthorized() throws Exception {
		jwt = "Bearer "+Jwts.builder().setSubject("user").claim("roles", "user").setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "secretkey").setExpiration(new Date(System.currentTimeMillis() - EXPIRATIONTIME)).compact();
		
		OrderRequestDto orderRequestDto = new OrderRequestDto();
		Mockito.when(deliveryService.closeOrder(Mockito.anyInt())).thenReturn(orderStatus);
		RequestBuilder request = MockMvcRequestBuilders.post("/delivery/closeOrder/1")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString((orderRequestDto)))
				.header(HttpHeaders.AUTHORIZATION, jwt);
		mockMvc.perform(request).andExpect(status().is(401)).andReturn();
	}

}
