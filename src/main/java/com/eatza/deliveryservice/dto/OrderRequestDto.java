package com.eatza.deliveryservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class OrderRequestDto {
private Long orderId;
private Long customerId;
public OrderRequestDto(Long orderId, Long customerId) {
	super();
	this.orderId = orderId;
	this.customerId = customerId;
}

}
