package com.projet.Chapi.services;


import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.projet.Chapi.models.Users;
import com.projet.Chapi.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
    public Optional<Users> findOneUserByExample(Users user){
        Example<Users> example = Example.of(user);
        return userRepository.findOne(example);
    }
    
    public Users loginVerification(Users user) {
    	if(userRepository.findByEmail(user.getEmail())!=null) {
    		Users dbUser = userRepository.findByEmail(user.getEmail());
    		if(encoder.matches(user.getPassword(),dbUser.getPassword())) return dbUser;
    		else return null;
    	} else {
    		return null;
    	}
    }

    public Users saveUser(Users user) {
    	setUserDate(user);
    	user.setPassword(encoder.encode(user.getPassword()));
    	return userRepository.save(user);
    }
    
    public Optional<Users> getUserById(final Long id) {
        return userRepository.findById(id);
    }
    
    public Users setUserDate(Users user) {
    	user.setCreated_at(LocalDate.now().toString());
    	user.setUpdated_at(LocalDate.now().toString());
    	return user;
    }

}
