package com.services;

import com.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
   
    @Autowired
    private UserIntImplService userPortRepository;      
 
    @Override 
    @Transactional 
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 
        User user = getUserByEmail(username); 
        return UserDetailsImpl.build(user); 
    } 
 
    private User getUserByEmail(String email) throws UsernameNotFoundException { 
        return userPortRepository.findByEmail(email) 
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no hallado: " + email)); 
    } 

}
