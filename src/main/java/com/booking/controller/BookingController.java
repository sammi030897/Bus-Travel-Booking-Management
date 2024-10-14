package com.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.payload.BusDTO;
import com.booking.payload.BusOperatorDTO;
import com.booking.payload.FeedbackDTO;
import com.booking.payload.PassengerDTO;
import com.booking.payload.RouteDTO;
import com.booking.payload.ScheduleDTO;
import com.booking.service.BusOperatorService;
import com.booking.service.BusService;
import com.booking.service.FeedbackService;
import com.booking.service.PassengerService;
import com.booking.service.RouteService;
import com.booking.service.ScheduleService;

@RestController
@RequestMapping("api/")
public class BookingController {
	private BusService busService;
	private BusOperatorService busOperatorService;
	private RouteService routeService;
	private ScheduleService scheduleService;
	private PassengerService passengerService;
	private FeedbackService feedbackService;

	@Autowired
	public BookingController(BusService busService, BusOperatorService busOperatorService, RouteService routeService,
			ScheduleService scheduleService, PassengerService passengerService, FeedbackService feedbackService) {
		this.busService = busService;
		this.busOperatorService = busOperatorService;
		this.routeService = routeService;
		this.scheduleService = scheduleService;
		this.passengerService = passengerService;
		this.feedbackService = feedbackService;
	}

//	http://localhost:8080/api/bus-opeartors
	@PostMapping("/bus-opeartors")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<BusOperatorDTO> createBusOperator(@RequestBody BusOperatorDTO busOperatorDTO) {
		BusOperatorDTO createbusoperater = busOperatorService.createBusOperator(busOperatorDTO);
		return new ResponseEntity<>(createbusoperater, HttpStatus.CREATED);
	}

//	http://localhost:8080/api/buses
	@PostMapping("/buses")
	@PreAuthorize("hasRole('ADMIN')")
	public List<BusDTO> createBuses(@RequestBody List<BusDTO> busDTOs) {
		return busService.createBuses(busDTOs);
	}

//	http://localhost:8080/api/bus/list
	@GetMapping("/bus/list")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<List<BusDTO>> getAllBuses() {
		List<BusDTO> buses = busService.getAllBuses();
		return new ResponseEntity<>(buses, HttpStatus.OK);
	}

//	http://localhost:8080/api/routes
	@PostMapping("/routes")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<RouteDTO>> createRoutes(@RequestBody List<RouteDTO> routeDTOs) {
		List<RouteDTO> createRoutes = routeService.createRoutes(routeDTOs);
		return new ResponseEntity<>(createRoutes, HttpStatus.CREATED);
	}

	@PostMapping("/schedules")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<ScheduleDTO>> createSchedules(@RequestBody ScheduleDTO scheduleDTO) {
		List<ScheduleDTO> createdSchedules = scheduleService.createSchedule(scheduleDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdSchedules);
	}

	@GetMapping("/schedules/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<ScheduleDTO> getSchedule(@PathVariable Long id) {
		ScheduleDTO retrievedSchedule = scheduleService.getSchedule(id);
		return ResponseEntity.ok(retrievedSchedule);
	}

//  http://localhost:8080/api/passengers
	@PostMapping("/passengers")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<PassengerDTO>> createPassenger(@RequestBody List<PassengerDTO> passengers) {
		List<PassengerDTO> createPassenger = passengerService.createPassenger(passengers);
		return new ResponseEntity<>(createPassenger, HttpStatus.CREATED);
	}

	@PostMapping("/feedback")
	@PreAuthorize("hasRole('USER')")
    public ResponseEntity<FeedbackDTO> submitFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        FeedbackDTO submittedFeedback = feedbackService.submitFeedback(feedbackDTO);
        return new ResponseEntity<>(submittedFeedback, HttpStatus.CREATED);
    }
}
