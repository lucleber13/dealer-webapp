package cbcoder.dealerwebapp.UsersInfo.services;

import cbcoder.dealerwebapp.UsersInfo.Dtos.UserDto;
import cbcoder.dealerwebapp.UsersInfo.model.User;

/**
 * <p>Copyright (c) 2024, Cleber Balbinote.</p>
 * <p>All rights reserved.</p>
 * <p>Licensed under the MIT License.</p>
 * <p>For full license text, please see the LICENSE file in the repo root or <a href="https://opensource.org/licenses/MI">...</a>T</p>
 * </br>
 * SuperAdminService interface provides the methods to add, revoke, and delete the super admin role.
 * It also provides the methods to create and revoke the super admin role.
 * The methods are implemented in the SuperAdminServiceImpl class.
 *
 * @author Cleber Balbinote
 * @version 1.0
 * @since 2024-06-15
 */
public interface SuperAdminService {

    User addAdminRole(UserDto userDto);

    User revokeAdminRole(Long userId);

    void deleteSuperAdminRole(Long userId);

    User createSuperAdmin(UserDto userDto);

    User revokeSuperAdminRole(Long userId);


}
