package com.eatza.deliveryservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eatza.deliveryservice.model.OrderStatus;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer> {

	Optional<OrderStatus> findByOrderId(Long long1);

}
