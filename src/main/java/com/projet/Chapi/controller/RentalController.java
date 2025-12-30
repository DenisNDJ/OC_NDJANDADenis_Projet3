package com.projet.Chapi.controller;

import org.modelmapper.internal.util.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.projet.Chapi.dto.RentalDto;
import com.projet.Chapi.dto.RentalsDto;
import com.projet.Chapi.dto.ResponseMessageDto;
import com.projet.Chapi.models.Rental;
import com.projet.Chapi.services.DtoService;
import com.projet.Chapi.services.JWTService;
import com.projet.Chapi.services.RentalService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api")
public class RentalController {
	@Autowired
	public RentalService rentalService;
	@Autowired
	public JWTService jwtService; 
	@Autowired
	public DtoService dtoService;
	
	@GetMapping("rentals/{id}")
	public ResponseEntity<RentalDto> getRentalWithId(@PathVariable("id") final Long id) {
		if(rentalService.getRentalById(id).isPresent()) {
			return ResponseEntity.ok(dtoService.convertRentalToDto(rentalService.getRentalById(id).get()));
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}
	
	@GetMapping("rentals")
	public ResponseEntity<RentalsDto> getRentals() {
		if(Iterables.getLength(rentalService.getRentals()) > 0) {
			RentalsDto rentals = new RentalsDto(dtoService.convertRentalsToDto(rentalService.getRentals()));
			return ResponseEntity.ok(rentals);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

	}
	
	@PostMapping(path="rentals", consumes= {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<ResponseMessageDto> createRental(	@RequestParam String name,
												@RequestParam String surface,
												@RequestParam String price,
												@RequestParam MultipartFile picture,
												@RequestParam String description,
												HttpServletRequest request){
		
		Rental rental = rentalService.setRental(name, surface, price, picture, description, request);
		Rental rentalDb = rentalService.saveRental(rental);
		if(rental.equals(rentalDb)) {
			return ResponseEntity.ok(dtoService.convertResponceMessageToDto("Rental created"));
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}
	
	@PutMapping(path="rentals/{id}", consumes= {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<ResponseMessageDto> updateRental(@PathVariable("id") final Long id, @ModelAttribute Rental rental){
		
			rentalService.updateRental(id,rental);
			return ResponseEntity.ok(dtoService.convertResponceMessageToDto("Rental updated"));
	}
	
}
