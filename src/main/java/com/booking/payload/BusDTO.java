package com.booking.payload;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.booking.entity.BusOperator;
import com.booking.entity.BusType;
import com.booking.entity.Route;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BusDTO {
	private Long id;

	private BusOperator busOperator;

	private BusType type;

	@NotNull(message = "Total seats cannot be empty")
	@Min(value = 1, message = "Total seats must be greater than 0")
	private int totalSeats;

	private List<TimeSlotDTO> timeSlots;
	private String amenities;
	
	
	private Date createdAt;

	private Date updatedAt;

	
	private Double price;
	
	private Route route;

}
