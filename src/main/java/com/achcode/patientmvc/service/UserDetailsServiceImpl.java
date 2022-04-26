package com.achcode.patientmvc.service;

import com.achcode.patientmvc.model.Users;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private SecurityService securityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users userApp = securityService.loadUserByUserName(username);

        if(userApp==null) throw new RuntimeException("User with username= "+username+" not found");
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        userApp.getRolesList().forEach(role->{
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });

        User user = new User(userApp.getUsername(),userApp.getPassword(),authorities);
        return user;
    }
}
