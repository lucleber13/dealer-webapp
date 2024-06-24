package cbcoder.dealerwebapp.UsersInfo.services;

import cbcoder.dealerwebapp.UsersInfo.Dtos.UserDto;
import cbcoder.dealerwebapp.UsersInfo.model.User;

public interface SuperAdminService {

    User addAdminRole(UserDto userDto);

    User revokeAdminRole(Long userId);

    void deleteSuperAdminRole(Long userId);

    User createSuperAdmin(UserDto userDto);

    User revokeSuperAdminRole(Long userId);


}
