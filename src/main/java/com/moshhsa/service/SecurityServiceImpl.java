package com.moshhsa.service;

import com.moshhsa.config.JwtTokenUtil;
import com.moshhsa.entites.User;
import com.moshhsa.exception.AuthenticationFailureException;
import com.moshhsa.model.AuthenticationRequest;
import com.moshhsa.model.AuthenticationResponse;
import com.moshhsa.model.UserModel;
import com.moshhsa.repository.UserRepository;
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

@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService{

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService customUserDetailsService;
    private final UserRepository userRepository;
    private final PasswordEncoder bcryptEncoder;
    private final MessageResource messageResource;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws AuthenticationFailureException {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return new AuthenticationResponse(token);
    }

    @Override
    public User registerUser(UserModel user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userRepository.save(newUser);
    }

    private void authenticate(String username, String password) throws AuthenticationFailureException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AuthenticationFailureException(messageResource.getMessage("account.suspended"));
        } catch (BadCredentialsException e) {
            throw new AuthenticationFailureException(messageResource.getMessage("invalid.credentials"));
        }
    }
}
