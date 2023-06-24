package vn.itech.com.quad_service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.itech.com.quad_service.payload.response.BaseResponse;
import vn.itech.com.quad_service.security.services.users.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserControler {

    private final UserService userService;

    public UserControler(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<?> getAll() {

        BaseResponse<?> baseResponse = userService.getUser();

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
