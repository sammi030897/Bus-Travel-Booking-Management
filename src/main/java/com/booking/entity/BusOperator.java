package com.booking.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

@Entity
@Table(name = "bus_operators")
public class BusOperator {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "operator_name")
	private String operatorName;

	@Column(name = "contact_email")
	private String contactEmail;

	@Column(name = "contact_phone")
	private String contactPhone;

	@Column(name = "logo_url")
	private String logoUrl;
	
	@JsonIgnore
	@Column(name = "created_at")
	private Date createdAt;
	
	@JsonIgnore
	@Column(name = "updated_at")
	private Date updatedAt;

	@JsonIgnore
	@OneToMany(mappedBy = "busOperator", cascade = CascadeType.ALL)
	private Set<Bus> bus;

}
