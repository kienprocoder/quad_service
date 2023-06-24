package vn.itech.com.quad_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.itech.com.quad_service.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
