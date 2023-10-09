package com.waff.rest.demo.controller;

import com.waff.rest.demo.dto.LoginRequestDto;
import com.waff.rest.demo.dto.RegisterRequestDto;
import com.waff.rest.demo.model.DaoUserDetails;
import com.waff.rest.demo.model.User;
import com.waff.rest.demo.model.UserType;
import com.waff.rest.demo.security.JwtUtils;
import com.waff.rest.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;


@RestController
@CrossOrigin("*")
public class LoginRegisterController {

    private final UserService userService;

    private final AuthenticationProvider authenticationProvider;
    private final ModelMapper modelMapper;


  @Autowired
  JwtUtils jwtUtils;

    public LoginRegisterController(UserService userService, AuthenticationProvider authenticationProvider, ModelMapper modelMapper) {
        this.userService = userService;
        this.authenticationProvider = authenticationProvider;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequestDto request, HttpServletRequest httpRequest) {
        try{
            // check credentials
            Authentication authenticate = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            // get user after success Authentication
            String jwt = jwtUtils.generateJwtToken(authenticate);
            DaoUserDetails principal = (DaoUserDetails) authenticate.getPrincipal();
            principal.setPassword(null);
            principal.setJwt(jwt);
            // authenticate context
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authenticate);

            
            // create cookie session after login
            HttpSession session = httpRequest.getSession(true);
            // save authenticated context in the session
            session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, securityContext);
            // add session to the response
            httpRequest.getSession(true);
            return ResponseEntity.ok(principal.getUser());
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterRequestDto request, HttpServletRequest httpRequest) {
        User user = modelMapper.map(request, User.class);
        user.setUserType(UserType.user);
        user.setEnabled(true);
        User created = userService.createUser(user).orElse(null);
        if(created != null) {
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
