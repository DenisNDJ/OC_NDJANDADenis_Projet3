package com.projet.Chapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projet.Chapi.dto.TokenDto;
import com.projet.Chapi.dto.UserDto;
import com.projet.Chapi.models.Users;
import com.projet.Chapi.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

import com.projet.Chapi.services.DtoService;
import com.projet.Chapi.services.JWTService;

@RestController
@RequestMapping("api")
public class LogController {
	
	@Autowired
	public JWTService jwtService;
	@Autowired
	public UserService userService;
	@Autowired
	public DtoService dtoService;
	
	@PostMapping("auth/login")
	public ResponseEntity<TokenDto> login(@RequestBody Users user){
		if(userService.loginVerification(user) !=null) {
			Users dbUser = userService.loginVerification(user);
			return ResponseEntity.ok(dtoService.convertTokenToDto(jwtService.generateToken(dbUser)));
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}	
	}

	@PostMapping("auth/register")
	public ResponseEntity<TokenDto> register(@RequestBody Users user) {
			Users dbUser = userService.saveUser(user);
			if(dbUser.equals(user)) {
				return ResponseEntity.ok(dtoService.convertTokenToDto(jwtService.generateToken(dbUser)));
			} else {
				return ResponseEntity.badRequest().body(null);
			}
	}
	
	@GetMapping("auth/me")
	public ResponseEntity<UserDto> auth(HttpServletRequest request) {
		if(userService.getUserById(Long.parseLong(jwtService.extractUserIdFromHttpRequest(request))).isPresent()) {
			return ResponseEntity.ok(dtoService.convertUserToDto(userService.getUserById(Long.parseLong(jwtService.extractUserIdFromHttpRequest(request))).get()));
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}

}