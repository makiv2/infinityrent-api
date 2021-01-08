package com.infinityrent.api.API.service;

import com.infinityrent.api.API.dto.request.LoginRequest;
import com.infinityrent.api.API.dto.request.SignupRequest;
import com.infinityrent.api.API.dto.response.JwtResponse;
import com.infinityrent.api.API.model.Role;
import com.infinityrent.api.API.model.RoleType;
import com.infinityrent.api.API.model.User;
import com.infinityrent.api.API.repository.UserRepository;
import com.infinityrent.api.API.security.model.UserDetailsImpl;
import com.infinityrent.api.API.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//TODO: Maybe split this up into AuthenticationService and UserService
@Service
public class UserService {

    private final RoleType DEFAULT_ROLE = RoleType.ROLE_USER;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    public UserService(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.jwtUtil = jwtUtil;
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void createUser(SignupRequest signupRequest) {
        final Set<Role> roles = new HashSet<>();
        final Role role = roleService.getRoleByName(DEFAULT_ROLE);
        roles.add(role);
        final User user = new User(
                signupRequest.getUsername(),
                passwordEncoder.encode(signupRequest.getPassword()),
                signupRequest.getEmail()
        );
        user.setRoles(roles);
        userRepository.save(user);
    }

    public JwtResponse authenticate(LoginRequest loginRequest) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String jwtToken = jwtUtil.generateJwtToken(authentication);
        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        final List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        final Claims jwtClaims = jwtUtil.getJwtClaims(jwtToken);
        return new JwtResponse(
                jwtToken,
                jwtClaims.getIssuedAt(),
                jwtClaims.getExpiration(),
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    public boolean exists(String username, String email) {
        return existsByEmail(email) || existsByUsername(username);
    }


}
