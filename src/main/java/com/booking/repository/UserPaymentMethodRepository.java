package com.booking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.entity.UserPaymentMethod;
@Repository
public interface UserPaymentMethodRepository extends JpaRepository<UserPaymentMethod, Long> {

	Optional<List<UserPaymentMethod>> findByClientSecret(String clientSecret);

	boolean existsByEmailAndPhoneNumberAndUsername(String email, String phoneNumber, String username);

	List<UserPaymentMethod> findByEmailAndPhoneNumberAndUsername(String email, String phoneNumber, String username);

	boolean existsByClientSecret(String clientSecret);

}
