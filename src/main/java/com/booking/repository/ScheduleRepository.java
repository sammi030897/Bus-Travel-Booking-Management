package com.booking.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.booking.entity.BusType;
import com.booking.entity.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

	Schedule existsByTotalSeats(BusType type);

	@Transactional
	@Modifying
	@Query("UPDATE Schedule b SET b.totalSeats = :totalSeats WHERE b.type = :type")
	void updateAvailableSeatsByType(@Param("type") BusType type, @Param("totalSeats") int totalSeats);

	boolean existsByTotalSeatsAndTypeAndOriginAndDestinationAndPrice(int totalSeats, BusType type, String origin,
			String destination, Double price);
}
