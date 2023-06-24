package vn.itech.com.quad_service.security.services.users;

import org.springframework.stereotype.Service;
import vn.itech.com.quad_service.constant.enums.ApiStatus;
import vn.itech.com.quad_service.model.User;
import vn.itech.com.quad_service.payload.response.BaseResponse;
import vn.itech.com.quad_service.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public BaseResponse<?> getAll() {
        List<User> lstUser = userRepository.findAll();
        if (lstUser.isEmpty()) {
            return BaseResponse.builder().status(ApiStatus.BAD_REQUEST.getStatus()).build();
        }
        return BaseResponse.builder().status(ApiStatus.SUCCESS.getStatus()).data(lstUser).build();
    }

    public BaseResponse<?> getUser() {
        List<User> lstUser = userRepository.getuser();
        if (lstUser.isEmpty()) {
            return BaseResponse.builder().status(ApiStatus.BAD_REQUEST.getStatus()).build();
        }
        return BaseResponse.builder().status(ApiStatus.SUCCESS.getStatus()).data(lstUser).build();
    }
}
