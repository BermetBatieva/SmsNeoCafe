package com.example.SmsNeoCafe.controller;

import com.example.SmsNeoCafe.dto.AuthenticationResponse;
import com.example.SmsNeoCafe.dto.PhoneNumber;
import com.example.SmsNeoCafe.dto.PhoneNumberDto;
import com.example.SmsNeoCafe.dto.UserDto;
import com.example.SmsNeoCafe.jwt.JwtUtils;
import com.example.SmsNeoCafe.service.SmsService;
import com.example.SmsNeoCafe.service.UserDetailsServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private SmsService smsService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UserDto userDto) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getPhoneNumber(),
                            userDto.getCode())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username and password", e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getPhoneNumber());

        return ResponseEntity.ok
                (new AuthenticationResponse(jwtUtils.
                        generateToken(userDetails)));
    }

    @ApiOperation(value = "id : 1-Admin,2-Waiter,3-Barista,4-Client")
    @PostMapping("/registration")
    ResponseEntity<String> sendUser(@RequestBody PhoneNumberDto phoneNumberDto){
        smsService.send(phoneNumberDto);
        return ResponseEntity.ok("user created");
    }

    @ApiOperation(value = "подтверждение активации")
    @PostMapping("/activate")
    public ResponseEntity<String> activate(@RequestBody UserDto userDto) {
        userDetailsService.activate(userDto);
        return ResponseEntity.ok("account activate");
    }

    @ApiOperation(value = " код для авторизации")
    @PostMapping("/auth")
    public ResponseEntity<String> auth(@RequestBody PhoneNumber phoneNumber) {

        if (userDetailsService.auth(phoneNumber)){
            smsService.send_auth(phoneNumber);
        }
        return ResponseEntity.ok("you have been sent an activation code");
    }

}

