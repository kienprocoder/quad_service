package vn.itech.com.quad_service.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.itech.com.quad_service.entity.User;

@Repository
public interface UserDao extends CrudRepository<User, String> {
}
