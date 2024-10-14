package com.booking.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.entity.Offer;
import com.booking.payload.OfferDTO;
import com.booking.repository.OfferRepository;

@Service
public class OfferServiceImpl implements OfferService {
	private final OfferRepository offerRepository;

	@Autowired
	public OfferServiceImpl(OfferRepository offerRepository) {
		this.offerRepository = offerRepository;
	}

	private List<OfferDTO> offers = new ArrayList<>();

	@Override
	public OfferDTO createOffer(OfferDTO offerDTO) {
		Offer offer = dtoToOffer(offerDTO);

		offer.setCreatedAt(new Date());
		offer.setUpdatedAt(new Date());

		Offer savedoffer = offerRepository.save(offer);
		offers.add(offerDTO);
		return offerToDto(savedoffer);
	}

	private Offer dtoToOffer(OfferDTO offerDTO) {
		Offer offer = new Offer();
		offer.setOfferName(offerDTO.getOfferName());
		offer.setPromoCode(offerDTO.getPromoCode());
		offer.setDiscountType(offerDTO.getDiscountType());
		offer.setDiscountValue(offerDTO.getDiscountValue());
		offer.setStartDate(offerDTO.getStartDate());
		offer.setEndDate(offerDTO.getEndDate());

		return offer;
	}

	private OfferDTO offerToDto(Offer offer) {
		OfferDTO offerDTO = new OfferDTO();
		offerDTO.setId(offer.getId());
		offerDTO.setOfferName(offer.getOfferName());
		offerDTO.setPromoCode(offer.getPromoCode());
		offerDTO.setDiscountType(offer.getDiscountType());
		offerDTO.setDiscountValue(offer.getDiscountValue());
		offerDTO.setStartDate(offer.getStartDate());
		offerDTO.setEndDate(offer.getEndDate());
		offerDTO.setCreatedAt(offer.getCreatedAt());
		offerDTO.setUpdatedAt(offer.getUpdatedAt());

		return offerDTO;
	}
	@Override
	public OfferDTO updateOffer(Long id, OfferDTO updatedOffer) {

		Optional<Offer> optionalExistingOffer = offerRepository.findById(id);

		if (optionalExistingOffer.isPresent()) {
			Offer existingOffer = optionalExistingOffer.get();

			// Update the fields with the values from updatedOffer
			if (updatedOffer.getOfferName() != null) {
				existingOffer.setOfferName(updatedOffer.getOfferName());
			}
			if (updatedOffer.getPromoCode() != null) {
				existingOffer.setPromoCode(updatedOffer.getPromoCode());
			}
			if (updatedOffer.getDiscountValue() != null) {
				existingOffer.setDiscountValue(updatedOffer.getDiscountValue());
			}
			if (updatedOffer.getStartDate() != null) {
				existingOffer.setStartDate(updatedOffer.getStartDate());
			}
			if (updatedOffer.getEndDate() != null) {
				existingOffer.setEndDate(updatedOffer.getEndDate());
			}

			Offer updatedOfferEntity = offerRepository.save(existingOffer);

			return  offerToDto(updatedOfferEntity);
		} else {
			return null; // Return null if the offer with the given ID is not found
		}
	}

	@Override
	public void deleteOffer(Long id) {

		if (!offerRepository.existsById(id)) {
			throw new NoSuchElementException("Offer with ID " + id + " not found");
		}

		offerRepository.deleteById(id);
	}

	@Override
	public List<OfferDTO> getAllOffers() {
		List<Offer> allOffers = offerRepository.findAll();
		return convertToDTOs(allOffers);
	}

	private List<OfferDTO> convertToDTOs(List<Offer> allOffers) {
		List<OfferDTO> offerDTOs = new ArrayList<>();

		for (Offer offer : allOffers) {
			OfferDTO offerDTO = new OfferDTO();
			offerDTO.setId(offer.getId());
			offerDTO.setOfferName(offer.getOfferName());
			offerDTO.setPromoCode(offer.getPromoCode());
			offerDTO.setDiscountType(offer.getDiscountType());
			offerDTO.setDiscountValue(offer.getDiscountValue());
			offerDTO.setStartDate(offer.getStartDate());
			offerDTO.setEndDate(offer.getEndDate());
			offerDTO.setCreatedAt(offer.getCreatedAt());
			offerDTO.setUpdatedAt(offer.getUpdatedAt());

			offerDTOs.add(offerDTO);
		}

		return offerDTOs;
	}

	@Override
	public Double getOfferDiscount(String promoCode) {
		Optional<Offer> optionalOffer = offerRepository.findByPromoCode(promoCode);
		return optionalOffer.map(Offer::getDiscountValue).orElse(null);
	}

}
