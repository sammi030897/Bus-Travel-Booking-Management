package com.booking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.entity.Route;
@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

	Optional<Route> findByIdAndOriginAndDestination(long id, String origin, String destination);

	Optional<List<Route>> findByOriginAndDestination(String origin, String destination);

}
