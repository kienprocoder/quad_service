package vn.itech.com.quad_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.itech.com.quad_service.payload.request.DepartmentRequest;
import vn.itech.com.quad_service.payload.response.BaseResponse;
import vn.itech.com.quad_service.security.services.users.DepartmentService;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/department/regist")
    public ResponseEntity<?> registDepartment(@Valid @RequestBody DepartmentRequest departmentRequest) {

        BaseResponse<?> baseRegist = departmentService.registDepartment(departmentRequest);

        return new ResponseEntity<>(baseRegist, HttpStatus.OK);
    }
}
