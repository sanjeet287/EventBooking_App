package com.paymentservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paymentservice.entity.PaymentTransaction;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentTransaction, Long> {

	Optional<PaymentTransaction> findByStripePaymentIntentId(String paymentIntentId);
}
