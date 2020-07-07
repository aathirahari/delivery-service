package com.eatza.deliveryservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter@Setter@NoArgsConstructor
public class OrderStatus {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	private Long orderId;
	private Long customerId;
	private String status;
	@OneToOne
	private DeliveryPerson deliveryPerson;
	public OrderStatus(Long orderId, Long customerId, String status, DeliveryPerson deliveryPerson) {
		super();
		this.orderId = orderId;
		this.customerId = customerId;
		this.status = status;
		this.deliveryPerson = deliveryPerson;
	}
	
	
}
