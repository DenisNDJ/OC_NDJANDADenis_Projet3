package com.projet.Chapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projet.Chapi.dto.UserDto;
import com.projet.Chapi.services.DtoService;
import com.projet.Chapi.services.UserService;

@RestController
@RequestMapping("api")
public class UserController {
	@Autowired
	public UserService userService; 
	@Autowired
	public DtoService dtoService;
	
	@GetMapping("user/{id}")
	public ResponseEntity<UserDto> getUser(@PathVariable("id") final Long id) {
		if(userService.getUserById(id).isPresent()) {
			return ResponseEntity.ok(dtoService.convertUserToDto(userService.getUserById(id).get()));
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}

}
