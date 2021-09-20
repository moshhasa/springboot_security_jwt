package com.moshhsa.controller;

import com.moshhsa.entites.User;
import com.moshhsa.exception.AuthenticationFailureException;
import com.moshhsa.model.AuthenticationRequest;
import com.moshhsa.model.AuthenticationResponse;
import com.moshhsa.model.PasswordRequest;
import com.moshhsa.model.UserModel;
import com.moshhsa.service.SecurityService;
import com.moshhsa.util.HttpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SecurityResource {

	private final SecurityService securityService;

	@PostMapping(value = "/authenticate")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest)
			throws AuthenticationFailureException {
		return ResponseEntity.ok(securityService.authenticate(authenticationRequest));
	}

	@PostMapping(value = "/register")
	public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody UserModel user) {
		return ResponseEntity.ok(securityService.registerUser(user));
	}

	@PostMapping(value = "/change-password")
	public ResponseEntity<AuthenticationResponse> updatePassword(@RequestBody PasswordRequest passwordRequest,
																 HttpServletRequest request) {
		passwordRequest.setToken(HttpUtil.extractTokenFromRequest(request));
		return ResponseEntity.ok(securityService.updatePassword(passwordRequest));
	}
}
