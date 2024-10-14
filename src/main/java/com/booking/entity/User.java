package com.booking.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor

@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	@Column(name="phone_number")
	private String phoneNumber;
	
	@Column(name="username")
	private String username;
	
	@Column(name="created_at")
	private Date createdAt;
	
	@Column(name="updated_at")
	private Date updatedAt;
	
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL)
	private Set<UserPaymentMethod> userpaymentmethods;
	
//	@OneToMany(mappedBy="user",cascade=CascadeType.ALL)
//	private Set<Booking> bookings;
	
//	@OneToMany(mappedBy="user",cascade=CascadeType.ALL)
//	private Set<Feedback> feedbacks;
	
	@ManyToMany(fetch =FetchType.EAGER)
	@JoinTable(
			name="user_roles",
			joinColumns=@JoinColumn(name="user_id"),
			inverseJoinColumns = @JoinColumn(name="role_id"))
	private Set<Role> roles = new HashSet<>();
	
}

