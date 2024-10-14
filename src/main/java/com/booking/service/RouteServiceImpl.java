package com.booking.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.entity.Route;
import com.booking.payload.RouteDTO;
import com.booking.repository.RouteRepository;

@Service
public class RouteServiceImpl implements RouteService {

	private final RouteRepository routeRepository;

	@Autowired
	public RouteServiceImpl(RouteRepository routerepository) {
		this.routeRepository = routerepository;
	}

	@Override
	public List<RouteDTO> createRoutes(List<RouteDTO> routeDTOs) {
	    List<RouteDTO> createdRoutes = new ArrayList<>();
	    
	    for (RouteDTO routeDTO : routeDTOs) {
	        Route route = dtoToRoute(routeDTO);
	        route.setCreatedAt(new Date());
	        route.setUpdatedAt(new Date());

	        Route savedRoute = routeRepository.save(route);
	        createdRoutes.add(routeToDto(savedRoute));
	    }

	    return createdRoutes;
	}

	
	    private Route dtoToRoute(RouteDTO routeDTO) {
		Route route = new Route();
		route.setOrigin(routeDTO.getOrigin());
		route.setDestination(routeDTO.getDestination());
		route.setDistance(routeDTO.getDistance());

		return route;
	}

	private RouteDTO routeToDto(Route route) {
		RouteDTO routeDto = new RouteDTO();
		routeDto.setId(route.getId());
		routeDto.setOrigin(route.getOrigin());
		routeDto.setDestination(route.getDestination());
		routeDto.setDistance(route.getDistance());
		routeDto.setCreatedAt(route.getCreatedAt());
		routeDto.setUpdatedAt(route.getUpdatedAt());

		return routeDto;
	}

	@Override
	public List<RouteDTO> getAllRoutes() {
		// Fetch routes from the repository
		List<Route> routes = routeRepository.findAll();

		// Convert Route objects to RouteDTO objects
		List<RouteDTO> routeDTOs = routes.stream().map(this::routeToDto).collect(Collectors.toList());

		return routeDTOs;
	}

}
