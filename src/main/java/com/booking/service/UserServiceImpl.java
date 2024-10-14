package com.booking.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.booking.entity.User;
import com.booking.payload.UserDTO;
import com.booking.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserDTO createUser(UserDTO userDTO) {
		User user = dtoToUser(userDTO);

		User savedUser = userRepository.save(user);
		return userToDto(savedUser);
	}

	private User dtoToUser(UserDTO userDTO) {
		User user = new User();
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		user.setPhoneNumber(userDTO.getPhoneNumber());
		return user;
	}
	
	private UserDTO userToDto(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		userDTO.setEmail(user.getEmail());
		userDTO.setPhoneNumber(user.getPhoneNumber());
		userDTO.setCreatedAt(user.getCreatedAt());
		userDTO.setUpdatedAt(user.getUpdatedAt());
		return userDTO;
	}

	@Override
	public void deleteUser(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
		userRepository.delete(user);
	}

	@Override
	public UserDTO updateUser(Long id, UserDTO userDTO) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setEmail(userDTO.getEmail());
		user.setPhoneNumber(userDTO.getPhoneNumber());

		if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
			user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		}
		user.setUpdatedAt(new Date());
		User updatedUser = userRepository.save(user);
		return userToDto(updatedUser);
	}

}
