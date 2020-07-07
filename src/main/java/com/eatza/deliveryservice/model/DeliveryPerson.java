package com.eatza.deliveryservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class DeliveryPerson {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private Boolean availability;

	public DeliveryPerson(int id, String name, Boolean availability) {
		super();
		this.id = id;
		this.name = name;
		this.availability = availability;
		
	}

}
