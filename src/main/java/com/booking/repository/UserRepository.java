package com.booking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

	Optional<User> findByEmail(String email);
	
	Optional<User> findByUsernameOrEmail(String username,String email);
	
	Optional<User> findByUsername(String username);

	boolean existsByIdAndEmailAndPhoneNumberAndUsername(long id, String email, String phoneNumber, String username);
	 
}
