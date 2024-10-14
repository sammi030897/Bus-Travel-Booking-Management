package com.booking.payload;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.booking.entity.Booking;
import com.booking.entity.Passenger;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PassengerDTO {
	private Long id;
	
	
	private String firstName;
	
	
	private String lastName;
	
	private int age;
	

	private String gender;
	
//	@NotNull(message = "Seat number cannot be empty")
//	@Min(value = 1, message = "Seat number must be greater than 0")
//	private Integer seatNumber;
//	private List<Passenger> passenger;
	private Date createdAt;
	private Date updatedAt;
//	private Booking booking;
}
