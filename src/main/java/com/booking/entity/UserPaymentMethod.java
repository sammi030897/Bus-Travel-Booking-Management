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

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

@Entity
@Table(name = "userpaymentmethods")
public class UserPaymentMethod {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "users_id", referencedColumnName = "id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "passenger_id", referencedColumnName = "id")
    private Passenger passenger;
	
	@Column(name = "amount")
	private Double amount;
	
	
	@Enumerated(EnumType.STRING)
	private BusType type;

	@Column(name = "description")
	private String description;

	@Column(name = "client_Secret")
	private String clientSecret;

	@Column(name = "status")
	private String status;
	
	@Column(name="email")
	private String email;
	
	@Column(name="phone_number")
	private String phoneNumber;
	
	@Column(name="username")
	private String username;

}
