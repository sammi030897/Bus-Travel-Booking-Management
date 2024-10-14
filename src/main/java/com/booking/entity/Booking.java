package com.booking.entity;

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
import javax.persistence.Transient;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

@Entity
@Table(name = "bookings")
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn
	@Transient
	private UserPaymentMethod payment;
	
	@Column(name = "booking_Status")
	private String status;
	
	@Column(name = "last_name")
	private String lastName;

	@Column(name = "age")
	private int age;

	@Column(name = "gender")
	private String gender;
	
	@Enumerated(EnumType.STRING)
	private BusType type;

	@Column(name = "TotalPricePaid")
	private Double amount;
	
	@Column(name = "firstName")
	private String firstName;
	
	@Transient
	private String clientSecret;
}
