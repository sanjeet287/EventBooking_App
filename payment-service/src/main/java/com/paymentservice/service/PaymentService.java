package com.paymentservice.service;

import com.paymentservice.dto.PaymentConfirmationDTO;
import com.paymentservice.dto.PaymentRequestDTO;
import com.paymentservice.dto.PaymentResponseDTO;

public interface PaymentService {

	PaymentResponseDTO initiatePayment(PaymentRequestDTO requestDTO);

	void confirmPayment(PaymentConfirmationDTO confirmationDTO);
}
