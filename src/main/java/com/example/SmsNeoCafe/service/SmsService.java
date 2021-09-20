package com.example.SmsNeoCafe.service;

import com.example.SmsNeoCafe.dto.CodeGeneration;
import com.example.SmsNeoCafe.dto.PhoneNumberDto;
import com.example.SmsNeoCafe.entity.ERole;
import com.example.SmsNeoCafe.entity.User;
import com.example.SmsNeoCafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Component
public class SmsService {



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    private final String ACCOUNT_SID ="AC4356753ae73da70f84113859073938ac";

    private final String AUTH_TOKEN = "ba3b2365d6072e11610e58d45341fd9b";

    private final String FROM_NUMBER = "+13477123939";

    public String send(PhoneNumberDto phoneNumberDto) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        CodeGeneration codeGeneration = new CodeGeneration();

        User users = new User();
        users.setPhoneNumber(phoneNumberDto.getPhoneNumber());

        if (userRepository.existsByPhoneNumber(phoneNumberDto.getPhoneNumber())) {
            return "Error: Phone is already in use!";
        }

        users.setRole(ERole.getRole(phoneNumberDto.getRoleId()));
        users.setActivationCode(encoder.encode(codeGeneration.getCode() + ""));
        userRepository.save(users);
        String code = codeGeneration.getCode() + " - this is your NeoCafe code.";
        Message message = Message.creator(new PhoneNumber(phoneNumberDto.getPhoneNumber()),
                        new PhoneNumber(FROM_NUMBER), code)
                .create();
        return "successfully added";
    }

    public void send_auth(com.example.SmsNeoCafe.dto.PhoneNumber phoneNumber) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        CodeGeneration codeGeneration = new CodeGeneration();

        User users = userRepository.findByPhoneNumber(phoneNumber.getPhoneNumber());
        users.setActivationCode(encoder.encode(codeGeneration.getCode() + ""));
        userRepository.save(users);
        String code = codeGeneration.getCode() + " - this is your NeoCafe code.";
        Message message = Message.creator(new PhoneNumber(phoneNumber.getPhoneNumber()),
                        new PhoneNumber(FROM_NUMBER), code)
                .create();
    }
}