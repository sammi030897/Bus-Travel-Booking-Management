package com.booking.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.booking.payload.OfferDTO;
import com.booking.service.OfferService;

@RestController
@RequestMapping("api/")
public class OfferController {

    private final OfferService offerService;

    @Autowired
    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }
//  http://localhost:8080/api/offer
    @PostMapping("/offer")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<OfferDTO> createOffer(@RequestBody OfferDTO offerDTO) {
        OfferDTO createdOffer = offerService.createOffer(offerDTO);
        return new ResponseEntity<>(createdOffer, HttpStatus.CREATED);
    }

//  http://localhost:8080/api
    @GetMapping("/offer/list")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<OfferDTO>> getAllOffers() {
        List<OfferDTO> offers = offerService.getAllOffers();
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }
////  http://localhost:8080/api/1
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OfferDTO> updateOffer(@PathVariable Long id, @RequestBody OfferDTO updatedOffer) {
        OfferDTO offer = offerService.updateOffer(id, updatedOffer);
        if (offer != null) {
            return new ResponseEntity<>(offer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/offer/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteOffer(@PathVariable Long id) {
        try {
            offerService.deleteOffer(id);
            return new ResponseEntity<>("Offer deleted successfully", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Offer not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while deleting the offer", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

