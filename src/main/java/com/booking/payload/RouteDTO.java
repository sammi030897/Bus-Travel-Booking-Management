package com.booking.payload;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.booking.entity.Schedule;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RouteDTO {
	private Long id;
	
	@NotEmpty(message = "Origin cannot be empty")
	@Size(min = 2, max = 100, message = "Origin must be between 2 and 100 characters")
	private String origin;
	
	@NotEmpty(message = "Destination cannot be empty")
	@Size(min = 2, max = 100,message="Destination must be between 2 and100 characters")
	private String destination;
	
	@NotNull(message = "Distance cannot be empty")
	private Double distance;
	
	private Date createdAt;
	private Date updatedAt;
	
	@JsonIgnore
	private Set<Schedule> Schedules;
}
