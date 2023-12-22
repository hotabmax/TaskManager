package com.hotabmax.taskmanager.controllers;

import com.hotabmax.taskmanager.dtos_error.AppError;
import com.hotabmax.taskmanager.dtos_jWT.JwtRequsest;
import com.hotabmax.taskmanager.dtos_jWT.JwtResponse;
import com.hotabmax.taskmanager.services.MyUserDetailsService;
import com.hotabmax.taskmanager.utils.JwtTokenUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    private final JwtTokenUtils jwtTokenUtils;
    private final MyUserDetailsService myUserDetailsService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(JwtTokenUtils jwtTokenUtils, MyUserDetailsService myUserDetailsService, AuthenticationManager authenticationManager) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.myUserDetailsService = myUserDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/authentication")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Create JWT token for access. Need fields 'username' and 'password' in JSON object.", required = true, content = @Content(schema = @Schema(implementation = JwtRequsest.class)))
    @Operation(summary = "Create JWT token for access and return back", responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppError.class)), description = "Wrong login or password")
    })
    private ResponseEntity<?> createAuthToken(@RequestBody JwtRequsest authRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(), authRequest.getPassword()
            ));
        } catch (BadCredentialsException e){
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Wrong login or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
