package vn.itech.com.quad_service.security.services.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import vn.itech.com.quad_service.constant.enums.ApiStatus;
import vn.itech.com.quad_service.model.Department;
import vn.itech.com.quad_service.payload.request.DepartmentRequest;
import vn.itech.com.quad_service.payload.response.BaseResponse;
import vn.itech.com.quad_service.repository.DepartmentRepository;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public BaseResponse<?> registDepartment(DepartmentRequest departmentRequest) {

        Department department = new Department();
        department.setName(departmentRequest.getName());
        department.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        departmentRepository.save(department);

        return BaseResponse.builder().status(ApiStatus.SUCCESS.getStatus()).message("Create Department successfully!").build();
    }
}
