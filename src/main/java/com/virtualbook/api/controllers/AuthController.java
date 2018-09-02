package com.virtualbook.api.controllers;

import java.net.URI;
import java.util.Collections;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.virtualbook.api.models.Role;
import com.virtualbook.api.models.RoleName;
import com.virtualbook.api.models.User;
import com.virtualbook.api.payloads.ApiResponse;
import com.virtualbook.api.payloads.JwtAuthenticationResponse;
import com.virtualbook.api.payloads.LoginRequest;
import com.virtualbook.api.payloads.SignUpRequest;
import com.virtualbook.api.repositories.RolesRepository;
import com.virtualbook.api.repositories.UsersRepository;
import com.virtualbook.api.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UsersRepository userRepo;
	
	@Autowired
	RolesRepository roleRepo;
	
	@Autowired 
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtTokenProvider tokenProvider;
	
	@PostMapping("/signing")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.getUsernameOrEmail(),
						loginRequest.getPassword()
				)
		);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = tokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest){
		if(userRepo.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity(new ApiResponse(false,"Username is already taken!"),
					HttpStatus.BAD_REQUEST);
		}
		
		if(userRepo.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity(new ApiResponse(false,"Email Address is already taken!"),
					HttpStatus.BAD_REQUEST);
		}
		
		User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getUsername(),
				signUpRequest.getEmail(), signUpRequest.getPassword());
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		Role userRole = roleRepo.findByName(RoleName.STANDARD_USER)
				.orElseThrow(() -> new EntityNotFoundException("User Role not set."));
		
		user.setRoles(Collections.singleton(userRole));
		
		User result = userRepo.save(user);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentContextPath().path("/api/users/{username}")
				.buildAndExpand(result.getUsername()).toUri();
		
		return ResponseEntity.created(location).body(new ApiResponse(true,"User registered successfully"));
	}
}
