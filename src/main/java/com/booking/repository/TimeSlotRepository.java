package com.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.entity.TimeSlot;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

}
