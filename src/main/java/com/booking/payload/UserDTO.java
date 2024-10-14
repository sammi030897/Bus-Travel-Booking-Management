package com.booking.payload;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.booking.entity.UserPaymentMethod;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
	private Long id;
	
	@NotEmpty(message = "First name cannot be empty")
	@Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
	private String firstName;
	
	@NotEmpty(message = "Last name cannot be empty")
	@Size(min = 2, max = 50,message="Last name must be between 2 and 50characters")
	private String lastName;
	
	@NotEmpty(message = "Email cannot be empty")
	@Email(message = "Invalid email format")
	private String email;
	
	private String name;
	private String password;
	private String username;
	private String phoneNumber;
	private Date createdAt;
	private Date updatedAt;
	private Set<UserPaymentMethod> userPaymentMethod;
}
