package com.booking.controller;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.payload.BookingDTO;
import com.booking.service.BookingService;
import com.itextpdf.text.DocumentException;

@RestController
@RequestMapping("api/")
public class BusBookingController {

	@Autowired
	private BookingService busBookingService;

    @PostMapping("/booking")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<BookingDTO>> createBusBookings(@RequestBody BookingDTO bookingDTO) {
        List<BookingDTO> bookingDTOs = busBookingService.createBusBooking(bookingDTO);
        return ResponseEntity.ok(bookingDTOs);
    }
//	http://localhost:8080/api/download/pdf
	@GetMapping(value = "/download/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> exportUsersToPdf() {
		try {
			ByteArrayInputStream pdfStream = busBookingService.exportBookingsToPdf();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=UserDetails.pdf");

			return ResponseEntity.ok().headers(headers).body(new InputStreamResource(pdfStream));
		} catch (DocumentException e) {
			System.out.println(e);
			return ResponseEntity.internalServerError().build();
		}
	}

}
