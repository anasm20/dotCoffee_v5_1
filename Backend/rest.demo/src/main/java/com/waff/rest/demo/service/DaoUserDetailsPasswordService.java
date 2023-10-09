package com.waff.rest.demo.service;

import com.waff.rest.demo.model.DaoUserDetails;
import com.waff.rest.demo.model.User;
import com.waff.rest.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Service;

@Service
public class DaoUserDetailsPasswordService implements UserDetailsPasswordService {

    private final UserRepository userRepository;

    public DaoUserDetailsPasswordService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails updatePassword(UserDetails userDetails, String password) {
        String username = userDetails.getUsername();
        User user = userRepository.findUserByUsername(username);
        user.setPassword(password);
        return new DaoUserDetails(userRepository.save(user));
    }
}


