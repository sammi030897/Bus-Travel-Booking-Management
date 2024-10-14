package com.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.payload.FeedbackDTO;
import com.booking.service.FeedbackService;

@RestController
@RequestMapping("/api")
public class UserController {
	private final FeedbackService feedbackService;

	@Autowired
	public UserController(FeedbackService feedbackService) {
		this.feedbackService = feedbackService;
	}

//	http://localhost:8080/api/users/user-feedback
//	@PostMapping("/user-feedback")
//	@PreAuthorize("hasRole('USER')")
//	public ResponseEntity<FeedbackDTO> createPassenger(@RequestBody FeedbackDTO feedbackDTO) {
//		FeedbackDTO createdPassenger = feedbackService.submitFeedback(feedbackDTO);
//		return ResponseEntity.status(HttpStatus.CREATED).body(createdPassenger);
//	}
}
