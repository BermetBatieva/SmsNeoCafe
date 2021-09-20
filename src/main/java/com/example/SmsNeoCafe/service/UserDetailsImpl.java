package com.example.SmsNeoCafe.service;

import com.example.SmsNeoCafe.entity.ERole;
import com.example.SmsNeoCafe.entity.Permission;
import com.example.SmsNeoCafe.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;


public class UserDetailsImpl implements UserDetails {
    private Set<GrantedAuthority> authorities;
    private User user;

    public UserDetailsImpl(User user, ERole role) {
        this.user = user;
        authorities = new HashSet<>();
        for(Permission permission: role.getPermissions())
            authorities.add(new SimpleGrantedAuthority(permission.toString()));
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getActivationCode();
    }

    @Override
    public String getUsername() {
        return user.getPhoneNumber();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
