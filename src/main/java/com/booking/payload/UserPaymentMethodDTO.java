package com.booking.payload;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.booking.entity.BusType;
import com.booking.entity.Passenger;
import com.booking.entity.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserPaymentMethodDTO {
	private Long id;

	private User user;

	@NotEmpty(message = "Amount  cannot be empty")
	@Size(min = 1, message = "please pay atleast one rupee")
	private Double amount;

	@NotEmpty(message = "Description  cannot be empty")
	@Size(min = 2, max = 100, message = "Description must be between 2 and 100 characters")
	private String description;
	private int totalSeats;
	private String clientSecret;
	private String promoCode;
	private BusType type;
	private List<Passenger> passenger;
	private String origin;
	private String destination;
	private String firstName;

}
