package com.booking.service;

import java.util.List;

import com.booking.payload.OfferDTO;

public interface OfferService {
	OfferDTO createOffer(OfferDTO offerDTO);

	void deleteOffer(Long id);

	Double getOfferDiscount(String promoCode);// for payment

	List<OfferDTO> getAllOffers();

	OfferDTO updateOffer(Long id, OfferDTO updatedOffer);


}
