package com.projet.Chapi.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.projet.Chapi.dto.TokenDto;
import com.projet.Chapi.dto.UserDto;
import com.projet.Chapi.models.Rental;
import com.projet.Chapi.models.Users;
import com.projet.Chapi.dto.ResponseMessageDto;
import com.projet.Chapi.dto.RentalDto;

@Service
public class DtoService {

    @Autowired
    public ModelMapper modelMapper;
    
	public TokenDto convertTokenToDto(String token) {
		return new TokenDto(token);
	}
	
	public ResponseMessageDto convertResponceMessageToDto(String message) {
		return new ResponseMessageDto(message);
	}
	
	public UserDto convertUserToDto(Users user) {
		return modelMapper.map(user, UserDto.class);
	}	
	
	public RentalDto convertRentalToDto(Rental rental) {
		return modelMapper.map(rental, RentalDto.class);
	}
	
	public List<RentalDto> convertRentalsToDto(Iterable<Rental> rentals) {
		return StreamSupport.stream(rentals.spliterator(), false)
		.map(this::convertRentalToDto)
		.collect(Collectors.toList());
	}
	
	
}
