package com.booking.service;

import com.booking.payload.UserDTO;

public interface UserService {

	public UserDTO createUser(UserDTO userDTO);

	public void deleteUser(Long id);

	public UserDTO updateUser(Long id, UserDTO userDTO);

}
