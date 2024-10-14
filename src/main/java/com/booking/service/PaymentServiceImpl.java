package com.booking.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.booking.entity.BusType;
import com.booking.entity.Passenger;
import com.booking.entity.UserPaymentMethod;

import com.booking.payload.UserPaymentMethodDTO;
import com.booking.repository.BusRepository;
import com.booking.repository.PassengerRepository;
import com.booking.repository.ScheduleRepository;
import com.booking.repository.UserPaymentMethodRepository;
import com.booking.repository.UserRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

@Service
public class PaymentServiceImpl implements PaymentService {

	private UserPaymentMethodRepository userPaymentMethodRepository;
	private UserRepository userRepository;
	private OfferService offerService;
	private BusRepository busRepository;
	private ScheduleRepository scheduleRepository;
	private PassengerRepository passengerRepository;

	@Autowired
	public PaymentServiceImpl(UserPaymentMethodRepository userPaymentMethodRepository, OfferService offerService,
			UserRepository userRepository, BusRepository busRepository, ScheduleRepository scheduleRepository,
			PassengerRepository passengerRepository) {
		this.userPaymentMethodRepository = userPaymentMethodRepository;
		this.offerService = offerService;
		this.userRepository = userRepository;
		this.busRepository = busRepository;
		this.scheduleRepository = scheduleRepository;
		this.passengerRepository = passengerRepository;
	}

	@Value("${stripe.apiKey}")
	private String stripeApiKey;

	@Override
	public String makePayment(UserPaymentMethodDTO userPaymentMethodDTO) {
		try {
			Stripe.apiKey = stripeApiKey;

			boolean user1 = userRepository.existsByIdAndEmailAndPhoneNumberAndUsername(
					userPaymentMethodDTO.getUser().getId(), userPaymentMethodDTO.getUser().getEmail(),
					userPaymentMethodDTO.getUser().getPhoneNumber(), userPaymentMethodDTO.getUser().getUsername());
			if (!user1) {
				throw new IllegalArgumentException("user details is wrong or doesn't exist");
			}

			Double price = userPaymentMethodDTO.getAmount(); // Assuming the DTO has a method to get the price directly
			BusType type = userPaymentMethodDTO.getType();
			int totalSeats = userPaymentMethodDTO.getTotalSeats();

			String origin = userPaymentMethodDTO.getOrigin();
			String destination = userPaymentMethodDTO.getDestination();
//			List<Passenger> passenger2 = userPaymentMethodDTO.getPassenger();
//			boolean allPassengersExist = passenger2.stream()
//			        .allMatch(passenger -> passengerRepository.existsByIdAndFirstNameAndLastNameAndAgeAndGender(
//			                passenger.getId(),
//			                passenger.getFirstName(),
//			                passenger.getLastName(),
//			                passenger.getAge(),
//			                passenger.getGender()
//			        ));
//			if (!allPassengersExist) {
//				throw new IllegalArgumentException("passenger given is wrong or duplicate entry");
//			}

			List<Passenger> passenger2 = userPaymentMethodDTO.getPassenger();

			Set<String> passengerDetailsSet = new HashSet<>(); // Set to track passenger details

			boolean allPassengersExist = passenger2.stream().allMatch(passenger -> {
				// Create a unique identifier for each passenger based on their details
				String passengerDetails = passenger.getFirstName() + passenger.getLastName() + passenger.getAge()
						+ passenger.getGender();

				// Check if this passenger's details have been seen before
				if (!passengerDetailsSet.contains(passengerDetails)) {
					// Passenger is unique, add to the set
					passengerDetailsSet.add(passengerDetails);
					return passengerRepository.existsByIdAndFirstNameAndLastNameAndAgeAndGender(passenger.getId(),
							passenger.getFirstName(), passenger.getLastName(), passenger.getAge(),
							passenger.getGender());
				} else {
					// Handle duplicate passenger entry (e.g., skip, log, or throw an exception)
					// For example, you can log a message or throw an exception here
					System.out.println("Duplicate passenger entry: " + passengerDetails);
					// Or throw an exception like this:
					// throw new IllegalArgumentException("Duplicate passenger entry: " +
					// passengerDetails);
					return false;
				}
			});

			if (!allPassengersExist) {

				throw new IllegalArgumentException("Passenger details are incorrect or duplicate entry");
			}
			boolean busExisting = busRepository.existsByTotalSeatsAndTypeAndOriginAndDestinationAndPrice(totalSeats, type,
					origin, destination,price);

			if (!busExisting) {
				throw new IllegalArgumentException("Please enter correct details metioned in schedule");
			}
			boolean scheduleExisting = scheduleRepository.existsByTotalSeatsAndTypeAndOriginAndDestinationAndPrice(totalSeats, type, origin, destination, price);

			Double discount = offerService.getOfferDiscount(userPaymentMethodDTO.getPromoCode());

			double calculatedAmount = userPaymentMethodDTO.getAmount();
			calculatedAmount *= passenger2.size();

			if (discount != null) {
				calculatedAmount *= (1 - discount);
			}

			if (user1 && busExisting && scheduleExisting && allPassengersExist) {
				PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder().setCurrency("INR")
						.setAmount((long) (calculatedAmount)).setDescription(userPaymentMethodDTO.getDescription())
						.build();
				PaymentIntent paymentIntent = PaymentIntent.create(createParams);

				userPaymentMethodDTO.setClientSecret(paymentIntent.getClientSecret());
				userPaymentMethodDTO.setAmount((calculatedAmount/=passenger2.size()));
				userPaymentMethodDTO.setDescription(userPaymentMethodDTO.getDescription());

//				UserPaymentMethod user = new UserPaymentMethod();
//				user.setAmount(calculatedAmount);
//				user.setDescription(userPaymentMethodDTO.getDescription());
//				user.setClientSecret(paymentIntent.getClientSecret());
//				user.setUser(userPaymentMethodDTO.getUser());
				for (Passenger passenger : passenger2) {
					// Create a separate UserPaymentMethod for each passenger
					UserPaymentMethod passengerUserPaymentMethod = new UserPaymentMethod();
					passengerUserPaymentMethod.setAmount(calculatedAmount);
					passengerUserPaymentMethod.setDescription(userPaymentMethodDTO.getDescription());
					passengerUserPaymentMethod.setClientSecret(paymentIntent.getClientSecret());
					passengerUserPaymentMethod.setUser(userPaymentMethodDTO.getUser());
					passengerUserPaymentMethod.setPassenger(passenger);
					passengerUserPaymentMethod.setType(userPaymentMethodDTO.getType());
					passengerUserPaymentMethod.setStatus("confirmed");
					passengerUserPaymentMethod.setEmail(userPaymentMethodDTO.getUser().getEmail());
					passengerUserPaymentMethod.setPhoneNumber(userPaymentMethodDTO.getUser().getPhoneNumber());
					passengerUserPaymentMethod.setUsername(userPaymentMethodDTO.getUser().getUsername());
					UserPaymentMethod save = userPaymentMethodRepository.save(passengerUserPaymentMethod);

				}
				// Save the passengerUserPaymentMethod

//				user.setType(userPaymentMethodDTO.getType());
//				user.setStatus("confirmed");
//				UserPaymentMethod save = userPaymentMethodRepository.save(user);

//				UserPaymentMethodDTO dto = new UserPaymentMethodDTO();
//
//				dto.setAmount(save.getAmount());
//				dto.setDescription(save.getDescription());
//				dto.setUser(save.getUser());
//				dto.setClientSecret(paymentIntent.getClientSecret());
				if (totalSeats > 0) {
					for (Passenger passengerDTO : passenger2) {
						totalSeats--;
//						busRepository.updateAvailableSeatsByType(type, totalSeats);

						busRepository.updateAvailableSeatsByTypeAndPriceAndTotalSeatsAndRoute(type, price, totalSeats,
								origin, destination);
						scheduleRepository.updateAvailableSeatsByType(type, totalSeats);
					}
				}
				return paymentIntent.getClientSecret() + ".........." + "Booking confirmed";
			} else {
				throw new IllegalArgumentException("User not found Or invalid payment amount or price/type is wrong");
			}
		} catch (StripeException e) {
			e.printStackTrace();
			return "Payment failed: " + e.getMessage();
		}

	}
}
