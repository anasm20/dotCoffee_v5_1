package com.waff.rest.demo.service;

import com.waff.rest.demo.model.DaoUserDetails;
import com.waff.rest.demo.model.User;
import com.waff.rest.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DaoUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public DaoUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(@NotBlank String username) {
        User user = userRepository.findUserByUsername(username);
        if (user != null) {
            return new DaoUserDetails(user);
        }
        else {
            throw new UsernameNotFoundException(username);
        }
    }
}


