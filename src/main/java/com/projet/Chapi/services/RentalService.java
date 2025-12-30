package com.projet.Chapi.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.projet.Chapi.models.Rental;
import com.projet.Chapi.repository.RentalRepository;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class RentalService {
	
	@Autowired
	public JWTService jwtService;
	
	@Value("${img.path}")
	private String imgPath;
	
	@Autowired
	private RentalRepository rentalRepository;
	
	public Iterable<Rental> getRentals(){
		return rentalRepository.findAll();
	}
	
    public Optional<Rental> getRentalById(final Long id) {
        return rentalRepository.findById(id);
    }
    
    public Rental saveRental(Rental rental) {
    	return rentalRepository.save(rental);
    }
    
    public Rental setRental(String name, String surface, String price, MultipartFile picture, String description, HttpServletRequest request) {
    	Rental rental = new Rental();
		rental.setName(name);
		rental.setSurface(Long.parseLong(surface));
		rental.setPrice(Long.parseLong(price));
		rental.setDescription(description);
		rental.setPicture(imgPath.concat(picture.getOriginalFilename()));
		rental.setOwner_id(Long.parseLong(jwtService.extractUserIdFromHttpRequest(request)));
		return rental;
    }
    
    public Rental updateRental(Long id, Rental rental) {
			Rental currentRental = getRentalById(id).get();
			
			if(rental.getName() != null) {
				currentRental.setName(rental.getName());
			}				
			if(rental.getDescription() != null) {
				currentRental.setDescription(rental.getDescription());
			}				
			if(rental.getPrice() != null) {
				currentRental.setPrice(rental.getPrice());
			}				
			if(rental.getSurface() != null) {
				currentRental.setSurface(rental.getSurface());
			}
			return saveRental(currentRental);
    }
}
