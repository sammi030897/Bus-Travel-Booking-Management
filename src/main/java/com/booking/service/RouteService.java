package com.booking.service;

import java.util.List;

import com.booking.payload.RouteDTO;

public interface RouteService {
	
	List<RouteDTO> getAllRoutes();

	List<RouteDTO> createRoutes(List<RouteDTO> routeDTOs);

}
