package com.booking.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.entity.Feedback;
import com.booking.payload.FeedbackDTO;
import com.booking.repository.FeedbackRepository;
import com.booking.repository.UserPaymentMethodRepository;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserPaymentMethodRepository paymentRepository;
    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository,UserPaymentMethodRepository paymentRepository) {
        this.feedbackRepository = feedbackRepository;
        this.paymentRepository=paymentRepository;
    }

    @Override
    public FeedbackDTO submitFeedback(FeedbackDTO feedbackDTO) {
        Feedback feedback = dtoToFeedback(feedbackDTO);

        String clientSecret = feedbackDTO.getPayment().getClientSecret();
		boolean existsByClientSecret = paymentRepository.existsByClientSecret(clientSecret);
		if (!existsByClientSecret) {
			throw new IllegalArgumentException("ClientSecret is wrong or doesn't exist");
		}
		feedback.setCreatedAt(new Date());
        feedback.setUpdatedAt(new Date());
        
        Feedback savedFeedback = feedbackRepository.save(feedback);
        
        return feedbackToDto(savedFeedback);
    }
   

  
    private Feedback dtoToFeedback(FeedbackDTO feedbackDTO) {
        Feedback feedback = new Feedback();
        feedback.setRating(feedbackDTO.getRating());
        feedback.setComments(feedbackDTO.getComments());
  
        return feedback;
    }

    private FeedbackDTO feedbackToDto(Feedback feedback) {
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        
        feedbackDTO.setRating(feedback.getRating());
        feedbackDTO.setComments(feedback.getComments());
        feedbackDTO.setCreatedAt(feedback.getCreatedAt());
        feedbackDTO.setUpdatedAt(feedback.getUpdatedAt());
    
        return feedbackDTO;
    }

}

