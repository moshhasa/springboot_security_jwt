package com.moshhsa.controller;

import com.moshhsa.entites.User;
import com.moshhsa.exception.AuthenticationFailureException;
import com.moshhsa.model.AuthenticationRequest;
import com.moshhsa.model.AuthenticationResponse;
import com.moshhsa.model.UserModel;
import com.moshhsa.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SecurityResource {

	private final SecurityService securityService;

	@PostMapping(value = "/authenticate")
	public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws AuthenticationFailureException {
		return ResponseEntity.ok(securityService.authenticate(authenticationRequest));
	}

	@PostMapping(value = "/register")
	public ResponseEntity<User> saveUser(@RequestBody UserModel user) {
		return ResponseEntity.ok(securityService.registerUser(user));
	}

}
