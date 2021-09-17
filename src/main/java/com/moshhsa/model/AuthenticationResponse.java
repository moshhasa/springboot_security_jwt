package com.moshhsa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse implements Serializable {
	private static final long serialVersionUID = -8091879091924046844L;
	private String username;
	private String token;
	private long expiresIn;
}