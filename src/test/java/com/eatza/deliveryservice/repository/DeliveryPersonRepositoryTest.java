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
		assertEquals(3, deliveryPerson.size());
	}
	
	@Test
	public void testFindByAvailability(){
		List<DeliveryPerson> deliveryPerson=deliveryPersonRepository.findByAvailability(true);
		assertEquals(2, deliveryPerson.size());
		deliveryPerson=deliveryPersonRepository.findByAvailability(false);
		assertEquals(1, deliveryPerson.size());
		
		
	}
	

}
