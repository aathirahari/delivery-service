package com.eatza.deliveryservice.repository;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.eatza.deliveryservice.model.DeliveryPerson;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DeliveryPersonRepositoryTest {

	@Autowired
	DeliveryPersonRepository deliveryPersonRepository;
	

	@Test
	public void findAll() {
		List<DeliveryPerson> deliveryPerson=deliveryPersonRepository.findAll();
		assertEquals(2, deliveryPerson.size());
	}
	

}
