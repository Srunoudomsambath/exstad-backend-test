package co.istad.exstadapi.features.user;

import co.istad.exstadapi.features.user.dto.UserRequest;
import co.istad.exstadapi.features.user.dto.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> getAllUsers();
    List<UserResponse> getNotScholarUsers();
    UserResponse getUserByUsername(String username);
    UserResponse getCurrentUser();
    String getUsernameFromAccessToken();
    UserResponse createUser(UserRequest userRequest);
    UserResponse getUserByUuid(String uuid);
    UserResponse getUserByEmail(String email);

}
