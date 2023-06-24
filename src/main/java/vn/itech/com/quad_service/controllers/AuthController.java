package vn.itech.com.quad_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.itech.com.quad_service.constant.enums.ApiStatus;
import vn.itech.com.quad_service.payload.request.LoginRequest;
import vn.itech.com.quad_service.payload.request.SignupRequest;
import vn.itech.com.quad_service.payload.response.BaseResponse;
import vn.itech.com.quad_service.payload.response.JwtResponse;
import vn.itech.com.quad_service.repository.RoleRepository;
import vn.itech.com.quad_service.repository.UserRepository;
import vn.itech.com.quad_service.security.jwt.JwtUtils;
import vn.itech.com.quad_service.security.services.UserDetailsImpl;
import vn.itech.com.quad_service.security.services.users.AuthService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final JwtUtils jwtUtils;

    private final AuthService authService;


    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, JwtUtils jwtUtils, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtUtils = jwtUtils;
        this.authService = authService;
    }

    @PostMapping("/signin")
    public BaseResponse<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        String getRole = String.valueOf(userDetails.getAuthorities());
        getRole.substring(1);

        return BaseResponse.builder()
                .status(ApiStatus.SUCCESS.getStatus())
                .data(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), getRole))
                .build();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        BaseResponse<?> baseSignUp = authService.registerUser(signUpRequest);

        return new ResponseEntity<>(baseSignUp, HttpStatus.OK);
    }
}
