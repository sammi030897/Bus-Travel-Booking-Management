package com.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
//	@Modifying
//	@Transactional
//	@Query("UPDATE Booking b SET b.remainingSeat = :remainingSeats WHERE b.bus.type = :type")
//	void updateByTypeAndRemainingSeats(@Param("type") BusType type, @Param("remainingSeats") int remainingSeats);
//	@Modifying
//	@Transactional
//	@Query("UPDATE Booking b SET b.remainingSeats = :remainingSeats WHERE b.bus.type = :type")
//	void updateRemainingSeatsByBusType(@Param("type") BusType type, @Param("remainingSeats") int remainingSeats);

}
