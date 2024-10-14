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
@Table(name="routes")
public class Route {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="origin")
	private String origin;
	
	@Column(name="destination")
	private String destination;
	
	@JsonIgnore
	@Column(name="distance")
	private Double distance;
	
	@JsonIgnore
	@Column(name="created_at")
	private Date createdAt;
	
	@JsonIgnore
	@Column(name="updated_at")
	private Date updatedAt;
	
	@JsonIgnore
	@OneToMany(mappedBy="route",cascade=CascadeType.ALL)
	private Set<Schedule> Schedules;	
	
}
