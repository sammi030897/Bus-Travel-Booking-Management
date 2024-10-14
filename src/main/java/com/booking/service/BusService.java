package com.booking.service;

import java.util.List;

import com.booking.payload.BusDTO;

public interface BusService {
	
	List<BusDTO> createBuses(List<BusDTO> busDTOs);

	List<BusDTO> getAllBuses();

}
