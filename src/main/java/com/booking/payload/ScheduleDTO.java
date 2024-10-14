package com.booking.payload;

import java.util.Date;
import java.util.List;

import com.booking.entity.BusType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ScheduleDTO {
	private Long id;
	private String origin;
	private String destination;
	private BusType type;
	private int totalSeats;
	private Double distance;
	private Double price;
	private List<TimeSlotDTO> timeSlots;

	private Date createdAt;
	private Date updatedAt;
}
