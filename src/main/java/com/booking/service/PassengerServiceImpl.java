package com.booking.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.entity.Passenger;
import com.booking.payload.PassengerDTO;
import com.booking.repository.PassengerRepository;

@Service
public class PassengerServiceImpl implements PassengerService {

	private final PassengerRepository passengerRepository;

	@Autowired
	public PassengerServiceImpl(PassengerRepository passengerRepository) {
		this.passengerRepository = passengerRepository;
	}

	private Passenger dtoToPassenger(PassengerDTO passengerDTO) {
		Passenger passenger = new Passenger();
		passenger.setFirstName(passengerDTO.getFirstName());
		passenger.setLastName(passengerDTO.getLastName());
		passenger.setAge(passengerDTO.getAge());
		passenger.setGender(passengerDTO.getGender());

		return passenger;
	}

	private PassengerDTO passengerToDto(Passenger passenger) {
		PassengerDTO passengerDTO = new PassengerDTO();
		passengerDTO.setId(passenger.getId());
		passengerDTO.setFirstName(passenger.getFirstName());
		passengerDTO.setLastName(passenger.getLastName());
		passengerDTO.setAge(passenger.getAge());
		passengerDTO.setGender(passenger.getGender());
		
		passengerDTO.setCreatedAt(passenger.getCreatedAt());
		passengerDTO.setUpdatedAt(passenger.getUpdatedAt());

		return passengerDTO;
	}

	@Override
	public List<PassengerDTO> createPassenger(List<PassengerDTO> passengers) {
	    List<PassengerDTO> passengerDTOs = new ArrayList<>();

	    for (PassengerDTO passengerDTO : passengers) {
	        Passenger passenger = dtoToPassenger(passengerDTO);

	        String firstName = passenger.getFirstName();
	        String lastName = passenger.getLastName();
	        Integer age = passenger.getAge();
	        String gender = passenger.getGender();

	        boolean existsByPassenger = passengerRepository.existsByFirstNameAndLastNameAndGenderAndAge(firstName, lastName, gender, age);

	        if (!existsByPassenger) {
	            // Passenger with the same details doesn't exist, proceed with saving
	            passenger.setCreatedAt(new Date());
	            passenger.setUpdatedAt(new Date());
	            Passenger savedPassenger = passengerRepository.save(passenger);

	            passengerDTOs.add(passengerToDto(savedPassenger));
	        } else {
	            // Passenger with the same details already exists, handle the error or return an appropriate response
	            throw new EntityNotFoundException("Passenger with the same details already exists");
	        }
	    }

	    return passengerDTOs;
	}

}
