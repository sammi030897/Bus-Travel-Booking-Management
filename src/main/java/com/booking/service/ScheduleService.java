package com.booking.service;

import java.util.List;

import com.booking.payload.ScheduleDTO;

public interface ScheduleService {

	ScheduleDTO getSchedule(Long id);

//	ScheduleDTO createSchedule(ScheduleDTO scheduleDTO);

	List<ScheduleDTO> createSchedule(ScheduleDTO scheduleDTO);

}
