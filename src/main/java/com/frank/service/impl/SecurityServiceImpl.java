package com.frank.service.impl;

import com.frank.entity.User;
import com.frank.entity.common.UserPrincipal;
import com.frank.repository.UserRepository;
import com.frank.service.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;

    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // we need to get our OWN user from database
        User user = userRepository.findByUsername(username);

        //return some exception if user doesn't exist
        if (user==null){
            throw new UsernameNotFoundException("This user does not exist");
        }

        //return user information in as a UserDetails
        return new UserPrincipal(user);
    }
}
