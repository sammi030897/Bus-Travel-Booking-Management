package com.booking.service;

import java.util.List;

import com.booking.payload.PassengerDTO;

public interface PassengerService {
	
	List<PassengerDTO> createPassenger(List<PassengerDTO> passengers);

}
