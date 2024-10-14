package com.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.booking.payload.UserPaymentMethodDTO;
import com.booking.service.PaymentService;

@RestController
@RequestMapping("api/")
public class PaymentController {

	@Value("${stripe.apikey}")
    private String stripeApiKey;
	
    private PaymentService paymentService;
    
    @Autowired
    public PaymentController(PaymentService paymentService) {
    	this.paymentService=paymentService;
    }
//	http://localhost:8080/api/make-payment
    @PostMapping("/make-payment")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<String> makePayment(@RequestBody UserPaymentMethodDTO userPaymentMethodDTO) {
        try {
            String clientSecret = paymentService.makePayment(userPaymentMethodDTO);
            return ResponseEntity.ok(clientSecret);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}



