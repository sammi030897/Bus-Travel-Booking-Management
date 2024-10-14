package com.booking.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.entity.Bus;
import com.booking.entity.BusOperator;
import com.booking.entity.BusType;
import com.booking.entity.Route;
import com.booking.entity.TimeSlot;
import com.booking.payload.BusDTO;
import com.booking.payload.TimeSlotDTO;
import com.booking.repository.BusOperatorRepository;
import com.booking.repository.BusRepository;
import com.booking.repository.RouteRepository;

@Service
public class BusServiceImpl implements BusService {

	private final BusRepository busRepository;
	private final BusOperatorRepository busOperatorRepository;
	private final RouteRepository routeRepository;

	@Autowired
	public BusServiceImpl(BusRepository busRepository, BusOperatorRepository busOperatorRepository,
			RouteRepository routeRepository) {
		this.busRepository = busRepository;
		this.busOperatorRepository = busOperatorRepository;
		this.routeRepository = routeRepository;
	}
	@Override
	public List<BusDTO> getAllBuses() {
	    List<Bus> buses = busRepository.findAll();
	    return buses.stream().map(this::busToDto).collect(Collectors.toList());
	}


	@Override
	public List<BusDTO> createBuses(List<BusDTO> busDTOs) {
		List<BusDTO> createdBuses = new ArrayList<>();

		for (BusDTO busDTO : busDTOs) {
			Bus bus = dtoToBus(busDTO);
			bus.setCreatedAt(new Date());
			bus.setUpdatedAt(new Date());

			Long busOperatorId = bus.getBusOperator().getId();
			String operatorName = bus.getBusOperator().getOperatorName();
			String contactEmail = bus.getBusOperator().getContactEmail();
			String contactPhone = bus.getBusOperator().getContactPhone();
			String logoUrl = bus.getBusOperator().getLogoUrl();
			Optional<BusOperator> optionalBusOperator = busOperatorRepository.findByIdAndOperatorNameAndContactEmailAndContactPhoneAndLogoUrl(busOperatorId,operatorName,contactEmail,contactPhone,logoUrl);
			
			long id = bus.getRoute().getId();
			String origin = bus.getRoute().getOrigin();
			String destination = bus.getRoute().getDestination();
			Optional<Route> findByIdAndOriginAndDestination = routeRepository.findByIdAndOriginAndDestination(id,origin, destination);
			
			if (optionalBusOperator.isPresent()&&findByIdAndOriginAndDestination.isPresent()) {
				allocateTimeSlot(busDTO, bus);
				bus.setOrigin(origin);
				bus.setDestination(destination);
				Bus savedBus = busRepository.save(bus);
				createdBuses.add(busToDto(savedBus));
			} else {
				throw new EntityNotFoundException("BusOperator not found or route(id and origin and destination) are incorrect");
			}
		}

		return createdBuses;
	}

	private Bus dtoToBus(BusDTO busDTO) {
		Bus bus = new Bus();
		bus.setBusOperator(busDTO.getBusOperator());
		bus.setRoute(busDTO.getRoute()); 
	
		bus.setType(busDTO.getType());
		bus.setAmenities(busDTO.getAmenities());
		bus.setTotalSeats(busDTO.getTotalSeats());
		bus.setPrice(busDTO.getPrice());
		return bus;
	}

	private BusDTO busToDto(Bus bus) {
		BusDTO busDto = new BusDTO();
		busDto.setId(bus.getId());
		busDto.setRoute(bus.getRoute());
		busDto.setBusOperator(bus.getBusOperator());
		busDto.setType(bus.getType());
		busDto.setAmenities(bus.getAmenities());
		busDto.setTotalSeats(bus.getTotalSeats());
		busDto.setCreatedAt(bus.getCreatedAt());
		busDto.setUpdatedAt(bus.getUpdatedAt());

		busDto.setPrice(bus.getPrice());

		List<TimeSlotDTO> timeSlotDTOs = bus.getTimeSlots().stream()
				.map(ts -> new TimeSlotDTO(ts.getStartTime(), ts.getEndTime())).collect(Collectors.toList());

		busDto.setTimeSlots(timeSlotDTOs.isEmpty() ? null : timeSlotDTOs);

		return busDto;
	}

	private void allocateTimeSlot(BusDTO busDTO, Bus bus) {
		LocalDateTime startTime = null;
		LocalDateTime endTime = null;
		Double price = null;

		List<TimeSlotDTO> timeSlots = busDTO.getTimeSlots();
		for (TimeSlotDTO timeSlotDTO : timeSlots) {
			if (bus.getType() == BusType.SEMI_SLIPPER || bus.getType() == BusType.SLIPPER
					|| bus.getType() == BusType.Executive_Berth || bus.getType() == BusType.AC
					|| bus.getType() == BusType.Semi_Executive_Berth) {
				startTime = timeSlotDTO.getStartTime();
				endTime = timeSlotDTO.getEndTime();
				price = bus.getPrice();
				break;
			}
		}

		if (startTime != null && endTime != null && price != null) {
			TimeSlot timeSlot = new TimeSlot();
			timeSlot.setStartTime(startTime);
			timeSlot.setEndTime(endTime);
			timeSlot.setBus(bus);
			bus.getPrice();
			bus.getTimeSlots().add(timeSlot);
		}
	}
}
