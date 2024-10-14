package com.booking.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.entity.Bus;
import com.booking.entity.BusType;
import com.booking.entity.Route;
import com.booking.entity.Schedule;
import com.booking.entity.TimeSlot;
import com.booking.payload.ScheduleDTO;
import com.booking.payload.TimeSlotDTO;
import com.booking.repository.BusRepository;
import com.booking.repository.RouteRepository;
import com.booking.repository.ScheduleRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class ScheduleServiceImpl implements ScheduleService {

	private final ScheduleRepository scheduleRepository;
	private final BusRepository busRepository;
	private final RouteRepository routeRepository;

	@Autowired
	public ScheduleServiceImpl(ScheduleRepository scheduleRepository, BusRepository busRepository,
			RouteRepository routeRepository) {
		this.scheduleRepository = scheduleRepository;
		this.busRepository = busRepository;
		this.routeRepository = routeRepository;
	}
	@Override
	@Transactional
	public List<ScheduleDTO> createSchedule(ScheduleDTO scheduleDTO) {
	    String origin = scheduleDTO.getOrigin();
	    String destination = scheduleDTO.getDestination();
	    BusType selectedBusType = scheduleDTO.getType();
	    Optional<List<Route>> findByOriginAndDestination = routeRepository.findByOriginAndDestination(origin, destination);
	    Optional<List<Bus>> optionalBuses = busRepository.findByType(selectedBusType);

	    if (!findByOriginAndDestination.isPresent() || findByOriginAndDestination.get().isEmpty()) {
	        throw new IllegalArgumentException("No route found for the given origin and destination.");
	    }
	    if (!optionalBuses.isPresent() || optionalBuses.get().isEmpty()) {
	        throw new IllegalArgumentException("No buses found for the given type.");
	    }
	    List<Bus> buses = optionalBuses.get();
	    List<ScheduleDTO> scheduleDTOs = new ArrayList<>();

	    for (Bus bus : buses) {
	        Route route = bus.getRoute();
	        
	        if (bus.getType().equals(selectedBusType) && route.getOrigin().equals(origin) && route.getDestination().equals(destination)) {
	            Schedule schedule = dtoToEntity(scheduleDTO);

	            schedule.setRoute(route);
	            schedule.setBus(bus);
	            schedule.setOrigin(route.getOrigin());
	            schedule.setDestination(route.getDestination());
	            schedule.setTotalSeats(bus.getTotalSeats());
	            schedule.setPrice(bus.getPrice());
	            schedule.setType(bus.getType());

	            Schedule savedSchedule = scheduleRepository.save(schedule);

	            ScheduleDTO savedScheduleDTO = entityToDto(savedSchedule);
	            scheduleDTOs.add(savedScheduleDTO);}
//	        }else {
//	        	throw new IllegalArgumentException("pl");
//	        }
	    }

	    return scheduleDTOs;
	}

	@Override
	public ScheduleDTO getSchedule(Long id) {
		Schedule schedule = scheduleRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Schedule not found"));

		return entityToDto(schedule);
	}
	private Schedule dtoToEntity(ScheduleDTO scheduleDTO) {
		Schedule schedule = new Schedule();
		
		schedule.setCreatedAt(new Date());
		schedule.setUpdatedAt(new Date());
		
		return schedule;
	}
	private ScheduleDTO entityToDto(Schedule schedule) {
		ScheduleDTO scheduleDTO = new ScheduleDTO();

		scheduleDTO.setId(schedule.getId());
		scheduleDTO.setType(schedule.getType());

		List<TimeSlot> timeSlots = schedule.getBus().getTimeSlots();
		List<TimeSlotDTO> timeSlotDTOs = new ArrayList<>();

		for (TimeSlot timeSlot : timeSlots) {

			if (timeSlot != null) {
				TimeSlotDTO timeSlotDTO = new TimeSlotDTO();
				timeSlotDTO.setStartTime(timeSlot.getStartTime());
				timeSlotDTO.setEndTime(timeSlot.getEndTime());

				timeSlotDTOs.add(timeSlotDTO);
			}
			scheduleDTO.setTimeSlots(timeSlotDTOs);
//			scheduleDTO.setDistance(schedule.getRoute().getDistance());
//			scheduleDTO.setPrice(schedule.getBus().getPrice());
			scheduleDTO.setOrigin(schedule.getOrigin());
			scheduleDTO.setDestination(schedule.getDestination());
			scheduleDTO.setCreatedAt(schedule.getCreatedAt());
			scheduleDTO.setUpdatedAt(schedule.getUpdatedAt());
			scheduleDTO.setTotalSeats(schedule.getTotalSeats());
			scheduleDTO.setPrice(schedule.getPrice());
			scheduleDTO.setDistance(schedule.getRoute().getDistance());
		}
		return scheduleDTO;
	}

}
