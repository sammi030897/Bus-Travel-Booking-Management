package com.booking.controller;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.entity.Role;
import com.booking.entity.User;
import com.booking.payload.LoginDTO;
import com.booking.payload.UserDTO;
import com.booking.repository.RoleRepository;
import com.booking.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

//	http://localhost:8080/api/auth
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
		if (userRepository.existsByUsername(userDTO.getUsername())) {
			return new ResponseEntity<>("UserName is already taken!!", HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByEmail(userDTO.getEmail())) {
			return new ResponseEntity<>("Email is already taken!!", HttpStatus.BAD_REQUEST);
		}

		User user = new User();
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setUsername(userDTO.getUsername());
		user.setEmail(userDTO.getEmail());
		user.setPhoneNumber(userDTO.getPhoneNumber());

		Date currentDate = new Date();
		user.setCreatedAt(currentDate);
		user.setUpdatedAt(currentDate);

		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

		Role role = new Role();
		if (userDTO.getName().equals("USER")) {
			role = roleRepository.findByName("ROLE_USER");
		} else if (userDTO.getName().equals("ADMIN")) {
			role = roleRepository.findByName("ROLE_ADMIN");
		}

		user.setRoles(Collections.singleton(role));

		userRepository.save(user);

		return new ResponseEntity<>("Register successfully", HttpStatus.OK);
	}
//	@PostMapping("/signin")
//    public ResponseEntity<String> signIn(@RequestBody LoginDTO loginDto) {
//        String role = determineRole(loginDto.getUsernameOrEmail());
//        if (role == null) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User does not exist or invalid role");
//        }
//        
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword())
//            );
//
//            if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(role))) {
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//                return ResponseEntity.ok("sign-in successfully with role: " + role);
//            } else {
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User does not have the required role");
//            }
//        } catch (AuthenticationException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
//        }
//    }
//
//	private String determineRole(String usernameOrEmail) {
//	    if ("admin@example.com".equals(usernameOrEmail)) {
//	        return "ROLE_ADMIN";
//	    } else {
//	        return "ROLE_USER";
//	    }
//	}

	@PostMapping("/signin/user")
	public ResponseEntity<String> signInUser(@RequestBody LoginDTO loginDto) {
		return signIn(loginDto, "ROLE_USER");
	}

	@PostMapping("/signin/admin")
	public ResponseEntity<String> signInAdmin(@RequestBody LoginDTO loginDto) {
		return signIn(loginDto, "ROLE_ADMIN");
	}

	private ResponseEntity<String> signIn(LoginDTO loginDto, String role) {
		try {
			// Authenticate the user
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

			// Check if the authenticated user has the specified role
			if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(role))) {
				SecurityContextHolder.getContext().setAuthentication(authentication);
				return ResponseEntity.ok("sign-in successfully with role: " + role);
			} else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User does not have the required role");
			}
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
		}
	}

	@PostMapping("/signout")
	public ResponseEntity<Map<String, String>> logout(HttpServletRequest request) {
		SecurityContextHolder.clearContext();

		Map<String, String> response = new HashMap<>();
		response.put("message", "logout successful");

		return ResponseEntity.ok(response);
	}
}
