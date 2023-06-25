package vn.itech.com.quad_service.security.services.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.itech.com.quad_service.constant.enums.ApiStatus;
import vn.itech.com.quad_service.model.ERole;
import vn.itech.com.quad_service.model.Role;
import vn.itech.com.quad_service.model.User;
import vn.itech.com.quad_service.payload.request.DepartmentRequest;
import vn.itech.com.quad_service.payload.request.SignupRequest;
import vn.itech.com.quad_service.payload.response.BaseResponse;
import vn.itech.com.quad_service.repository.RoleRepository;
import vn.itech.com.quad_service.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Signup
     * @param signUpRequest
     * @return
     */
    public BaseResponse<?> registerUser(SignupRequest signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return BaseResponse.builder().status(ApiStatus.BAD_REQUEST.getStatus()).message("Error: Username is already taken!").build();
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return BaseResponse.builder().status(ApiStatus.BAD_REQUEST.getStatus()).message("Error: Email is already in use!").build();
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setEmail(signUpRequest.getEmail());
        user.setAddress(signUpRequest.getAddress());
        user.setPhone(signUpRequest.getPhone());
        userRepository.save(user);

        return BaseResponse.builder().status(ApiStatus.SUCCESS.getStatus()).message("User registered successfully!").build();
    }
}