package com.booking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.entity.BusOperator;
@Repository
public interface BusOperatorRepository extends JpaRepository<BusOperator,Long> {

	Optional<BusOperator> findByIdAndOperatorNameAndContactEmailAndContactPhoneAndLogoUrl(Long busOperatorId, String operatorName,
			String contactEmail, String contactPhone, String logoUrl);

}
