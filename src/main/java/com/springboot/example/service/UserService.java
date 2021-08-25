package com.springboot.example.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.springboot.example.dto.UserRegistrationDto;
import com.springboot.example.model.User;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);
}
