package com.example.SmsNeoCafe.service;

import com.example.SmsNeoCafe.dto.PhoneNumber;
import com.example.SmsNeoCafe.dto.UserDto;
import com.example.SmsNeoCafe.entity.ERole;
import com.example.SmsNeoCafe.entity.User;
import com.example.SmsNeoCafe.exception.ResourceNotFoundException;
import com.example.SmsNeoCafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        if(user == null)
            throw new UsernameNotFoundException("Invalid phone or code.");
        ERole role = user.getRole();
        return new UserDetailsImpl(user,role);
    }


    public String activate(UserDto userDto) {
        User user = userRepository.findByPhoneNumber(userDto.getPhoneNumber());
        if (user.getActivationCode() == encoder.encode(userDto.getCode()))
            user.setActive(true);
        else
            throw  new ResourceNotFoundException("invalid code");
        userRepository.save(user);
        return "Account activated";
    }


    public boolean auth(PhoneNumber phoneNumber){
        User user = userRepository.findByPhoneNumber(phoneNumber
                .getPhoneNumber());
        if ( user != null){
            return user.isActive();
        }
        return false;
    }
}
