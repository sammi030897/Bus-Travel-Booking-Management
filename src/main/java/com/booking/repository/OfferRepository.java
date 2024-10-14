package com.booking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.entity.Offer;
@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

	Optional<Offer> findByPromoCode(String promoCode);

}
