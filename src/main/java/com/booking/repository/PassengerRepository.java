package com.booking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.entity.Passenger;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

	boolean existsByFirstNameAndLastNameAndGenderAndAge(String firstName, String lastName, String gender, int age);

	boolean existsByIdAndFirstNameAndLastNameAndAgeAndGender(Long id, String firstName, String lastName, int age,
			String gender);

	Optional<Passenger> findByFirstName(String firstName);
	
//	@Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Passenger p WHERE p IN :passengers")
//	boolean existsByIn(@Param("passengers") List<Passenger> passengers);

}
