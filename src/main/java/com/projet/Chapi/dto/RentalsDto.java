package com.projet.Chapi.dto;

import java.util.List;

import lombok.Data;

@Data
public class RentalsDto {
	private List<RentalDto> rentals;

	public RentalsDto(List<RentalDto> rentals) {
		this.rentals = rentals;
	}
	
	public List<RentalDto> getRentals() {
		return rentals;
	}

	public void setRentals(List<RentalDto> rentals) {
		this.rentals = rentals;
	}
	
}
