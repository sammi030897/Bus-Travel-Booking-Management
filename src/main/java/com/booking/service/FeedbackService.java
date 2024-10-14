package com.booking.service;

import com.booking.payload.FeedbackDTO;

public interface FeedbackService {
	FeedbackDTO submitFeedback(FeedbackDTO feedbackDTO);
}
