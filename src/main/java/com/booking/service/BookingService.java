package com.booking.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import com.booking.payload.BookingDTO;
import com.itextpdf.text.DocumentException;

public interface BookingService {

	List<BookingDTO> createBusBooking(BookingDTO booking);

	ByteArrayInputStream exportBookingsToPdf() throws DocumentException;

}

