package com.hotabmax.taskmanager.controllers;

import com.hotabmax.taskmanager.dtos.AppError;
import com.hotabmax.taskmanager.dtos.JwtRequsest;
import com.hotabmax.taskmanager.dtos.JwtResponse;
import com.hotabmax.taskmanager.services.MyUserDetailsService;
import com.hotabmax.taskmanager.utils.JwtTokenUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AuthenticationController {
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/authentication")
    @Operation(summary = "Create JWT token for access and return back")
    private ResponseEntity<?> createAuthToken(@io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                description = "Create JWT token for access. Need fields 'username' and 'password' in JSON object.",
                                                required = true,
                                                content = @Content(
                                                schema = @Schema(implementation = JwtRequsest.class)))@RequestBody JwtRequsest authRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(), authRequest.getPassword()
            ));
        } catch (BadCredentialsException e){
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Wrong login or password "), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
