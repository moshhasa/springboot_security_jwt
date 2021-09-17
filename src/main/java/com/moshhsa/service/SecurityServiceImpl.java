package com.moshhsa.service;

import com.moshhsa.config.JwtTokenUtil;
import com.moshhsa.entites.User;
import com.moshhsa.exception.AuthenticationFailureException;
import com.moshhsa.exception.ResourceNotFoundException;
import com.moshhsa.model.AuthenticationRequest;
import com.moshhsa.model.AuthenticationResponse;
import com.moshhsa.model.PasswordRequest;
import com.moshhsa.model.UserModel;
import com.moshhsa.repository.UserRepository;
import com.moshhsa.util.DateUtil;
import com.moshhsa.util.MessageResource;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService customUserDetailsService;
    private final UserRepository userRepository;
    private final PasswordEncoder bcryptEncoder;
    private final MessageResource messageResource;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws AuthenticationFailureException {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        return getAuthenticationResponse(authenticationRequest.getUsername());
    }

    @Override
    public AuthenticationResponse registerUser(UserModel user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        userRepository.save(newUser);
        return getAuthenticationResponse(user.getUsername());
    }


    @Override
    public AuthenticationResponse updatePassword(PasswordRequest passwordRequest) throws SecurityException, ResourceNotFoundException {
        final String username = jwtTokenUtil.extractUsername(passwordRequest.getToken());
        final User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException("User not Found");
        }

        if (!bcryptEncoder.matches(passwordRequest.getOldPassword(), user.getPassword())) {
            throw new SecurityException("you've entered an incorrect password");
        }
        user.setPassword(bcryptEncoder.encode(passwordRequest.getNewPassword()));
        this.userRepository.save(user);
        return getAuthenticationResponse(user.getUsername());
    }

    private AuthenticationResponse getAuthenticationResponse(String username) {
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);
        final Date expiryDate = jwtTokenUtil.extractExpirationDate(token);
        return new AuthenticationResponse(username, token, DateUtil.diffInSeconds(new Date(), expiryDate));
    }

    private void authenticate(String username, String password) throws AuthenticationFailureException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AuthenticationFailureException(messageResource.getMessage("account.suspended"));
        } catch (BadCredentialsException e) {
            throw new AuthenticationFailureException(messageResource.getMessage("invalid.credentials"));
        } catch (Exception e) {
            throw new AuthenticationFailureException(messageResource.getMessage("authentication.failure"));
        }
    }
}
