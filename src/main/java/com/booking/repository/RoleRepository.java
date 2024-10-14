package com.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.entity.Role;
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

//	Optional<Role> findByName(String name);
	Role findByName(String name);
}
