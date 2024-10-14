package com.booking.payload;

import com.booking.entity.BusType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookingDTO {
	@JsonIgnore
	private long id;
	private String firstName;
	private String lastName;
	private int age;
	private String gender;
	private Double amount;
	private String status;
	private BusType type;
	 
	private String clientSecret;

}
