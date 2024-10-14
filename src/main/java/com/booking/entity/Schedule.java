package com.booking.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

@Entity
@Table(name = "schedules")
public class Schedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "bus_id", referencedColumnName = "id")
	private Bus bus;

	@ManyToOne
	@JoinColumn(name = "route_id", referencedColumnName = "id")
	private Route route;

	@Enumerated(EnumType.STRING)
	private BusType type;

	@Column(name = "origin")
	private String origin;

	@Column(name = "price")
	private Double price;
	
	@Column(name = "available_seat")
	private int totalSeats;
	
	@Column(name = "destination")
	private String destination;
	
	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;
	    
}
