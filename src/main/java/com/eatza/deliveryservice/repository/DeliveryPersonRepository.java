package com.eatza.deliveryservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eatza.deliveryservice.model.DeliveryPerson;
import com.google.common.base.Optional;

public interface DeliveryPersonRepository extends JpaRepository<DeliveryPerson, Integer>{


	List<DeliveryPerson> findByAvailability(boolean b);


}
