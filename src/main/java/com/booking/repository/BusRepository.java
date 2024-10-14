package com.booking.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.booking.entity.Bus;
import com.booking.entity.BusType;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {

	@Transactional
	@Modifying
	@Query("UPDATE Bus b SET b.totalSeats = :totalSeats WHERE b.type = :type AND b.price = :price AND b.origin = :origin AND b.destination = :destination")
	void updateAvailableSeatsByTypeAndPriceAndTotalSeatsAndRoute(@Param("type") BusType type,
			@Param("price") Double price, @Param("totalSeats") int totalSeats, @Param("origin") String origin,
			@Param("destination") String destination);

	Optional<List<Bus>> findByType(BusType selectedBusType);

	boolean existsByTotalSeatsAndTypeAndOriginAndDestinationAndPrice(int totalSeats, BusType type, String origin,
			String destination, Double price);

}
