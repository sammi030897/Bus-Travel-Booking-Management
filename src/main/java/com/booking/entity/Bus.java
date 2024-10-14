package com.booking.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

@Entity
@Table(name = "buses")
public class Bus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "operator_id", referencedColumnName = "id")
	private BusOperator busOperator;

	@Enumerated(EnumType.STRING)
	private BusType type;

	@Column(name = "amenities")
	private String amenities;

	@Column(name = "total_seats")
	private int totalSeats;

//	@PrePersist
//	public void prePersist() {
//	        if (type == BusType.AC) {
//	            totalSeats = 20;
//	        }else if (type == BusType.SLIPPER) {
//	            totalSeats = 50;
//	        } else if (type == BusType.SEMI_SLIPPER) {
//	            totalSeats = 30;
//	        } else if (type == BusType.Executive_Berth) {
//	            totalSeats = 15;
//	        } else if (type == BusType.Semi_Executive_Berth) {
//	            totalSeats = 18;
//	        }
//	    }
	
	@Column(name = "price")
	private Double price;
	
	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;
	
	@OneToMany(mappedBy = "bus", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TimeSlot> timeSlots = new ArrayList<>();

	@OneToMany(mappedBy = "bus", cascade = CascadeType.ALL)
	private Set<Schedule> Schedules;
	
	@ManyToOne
	@JoinColumn(name = "route_id", referencedColumnName = "id")
	private Route route;

	@Column(name="origin")
	private String origin;
	
	@Column(name="destination")
	private String destination;
}
