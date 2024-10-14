package com.booking.service;

import com.booking.payload.UserPaymentMethodDTO;

public interface PaymentService {

	String makePayment(UserPaymentMethodDTO userPaymentMethodDTO);
}

