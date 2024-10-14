package com.booking.service;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.entity.Booking;
import com.booking.entity.Passenger;
import com.booking.entity.UserPaymentMethod;
import com.booking.payload.BookingDTO;
import com.booking.repository.BookingRepository;
import com.booking.repository.UserPaymentMethodRepository;
import com.booking.util.PdfBookingDetail;
import com.itextpdf.text.DocumentException;

@Service
public class BookingServiceImpl implements BookingService {
	private final BookingRepository bookingRepository;
	private final UserPaymentMethodRepository paymentRepository;

	@Autowired
	public BookingServiceImpl(BookingRepository bookingRepository,
	UserPaymentMethodRepository paymentRepository) {
		this.bookingRepository = bookingRepository;
		this.paymentRepository = paymentRepository;
	}
	
	@Override
	public List<BookingDTO> createBusBooking(BookingDTO booking) {
	    String clientSecret = booking.getClientSecret();

	    Optional<List<UserPaymentMethod>> findByClientSecret = paymentRepository.findByClientSecret(clientSecret);
	    boolean existsByClientSecret = paymentRepository.existsByClientSecret(clientSecret);
	    if (existsByClientSecret) {
	        List<UserPaymentMethod> userPaymentMethods = findByClientSecret.get();
	        List<BookingDTO> bookingDTOs = new ArrayList<>();

	        for (UserPaymentMethod userPaymentMethod : userPaymentMethods) {
	            Passenger passenger = userPaymentMethod.getPassenger();

	            Booking booking1 = dtoToBooking(booking);
	            booking1.setFirstName(passenger.getFirstName());
	            booking1.setLastName(passenger.getLastName());
	            booking1.setAge(passenger.getAge());
	            booking1.setGender(passenger.getGender());
	            booking1.setType(userPaymentMethod.getType());
	            booking1.setAmount(userPaymentMethod.getAmount());
	            booking1.setStatus(userPaymentMethod.getStatus());
	            booking1.setClientSecret(userPaymentMethod.getClientSecret());
	            
	            Booking savedBooking = bookingRepository.save(booking1);

	            bookingDTOs.add(bookingToDto(savedBooking));
	        }

	        return bookingDTOs;
	    } else {
	        throw new IllegalArgumentException("clientSecret is not correct or doesnot exist ");
	    }
	}


	private Booking dtoToBooking(BookingDTO bookingDTO) {
		Booking booking = new Booking();
	
		return booking;
	}
	private BookingDTO bookingToDto(Booking booking) {
	    BookingDTO bookingDTO = new BookingDTO();
	    
	    bookingDTO.setFirstName(booking.getFirstName());
	    bookingDTO.setLastName(booking.getLastName());
	    bookingDTO.setAge(booking.getAge());
	    bookingDTO.setGender(booking.getGender());
	    bookingDTO.setType(booking.getType());
	    bookingDTO.setAmount(booking.getAmount());
	    bookingDTO.setStatus(booking.getStatus());
	    bookingDTO.setClientSecret(booking.getClientSecret());
	    return bookingDTO;
	}

	 public ByteArrayInputStream exportBookingsToPdf() throws DocumentException {
	        List<Booking> findAll = bookingRepository.findAll();
	        List<BookingDTO> bookingDTOs = convertToDTOs(findAll);
	        
	        return PdfBookingDetail.exportBookingsToPdf(bookingDTOs);
	    }

	    private List<BookingDTO> convertToDTOs(List<Booking> bookings) {
	       
	        return bookings.stream()
	            .map(this::convertToDTO)
	            .collect(Collectors.toList());
	    }

	    private BookingDTO convertToDTO(Booking booking) {
	     
	        BookingDTO bookingDTO = new BookingDTO();
	        bookingDTO.setId(booking.getId());
	        bookingDTO.setFirstName(booking.getFirstName());
	        bookingDTO.setLastName(booking.getLastName());
	        bookingDTO.setAge(booking.getAge());
	        bookingDTO.setGender(booking.getGender());
	        bookingDTO.setAmount(booking.getAmount());
	        bookingDTO.setStatus(booking.getStatus());
	        bookingDTO.setType(booking.getType());
	        return bookingDTO;
	    }

}
