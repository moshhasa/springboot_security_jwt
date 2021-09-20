package com.moshhsa.service;

import com.moshhsa.exception.AuthenticationFailureException;
import com.moshhsa.exception.ResourceNotFoundException;
import com.moshhsa.model.AuthenticationRequest;
import com.moshhsa.model.AuthenticationResponse;
import com.moshhsa.model.PasswordRequest;
import com.moshhsa.model.UserModel;

public interface SecurityService {

    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws AuthenticationFailureException;

    AuthenticationResponse registerUser(UserModel user);

    AuthenticationResponse updatePassword(PasswordRequest passwordRequest) throws SecurityException;
}
