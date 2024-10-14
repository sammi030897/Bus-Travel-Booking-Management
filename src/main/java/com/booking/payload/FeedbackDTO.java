package com.booking.payload;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.booking.entity.UserPaymentMethod;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FeedbackDTO {
	
	@NotNull(message = "Rating cannot be empty")
	@Min(value = 1, message = "Rating must be between 1 and 5")
	@Max(value = 5, message = "Rating must be between 1 and 5")
	private Integer rating;

	private String comments;
	private Date createdAt;
	private Date updatedAt;

	private UserPaymentMethod payment;
	
}

