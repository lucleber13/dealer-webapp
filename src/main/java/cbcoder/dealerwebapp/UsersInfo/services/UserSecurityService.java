package cbcoder.dealerwebapp.UsersInfo.services;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * <p>Copyright (c) 2024, Cleber Balbinote.</p>
 * <p>All rights reserved.</p>
 * <p>Licensed under the MIT License.</p>
 * <p>For full license text, please see the LICENSE file in the repo root or <a href="https://opensource.org/licenses/MI">...</a>T</p>
 * </br>
 * UserSecurityService interface provides the userDetailsService method.
 * The userDetailsService method is used to retrieve the user details from the database.
 *
 * @author Cleber Balbinote
 * @version 1.0
 * @see UserDetailsService
 * @since 2024-06-15
 */
public interface UserSecurityService {

    UserDetailsService userDetailsService();
}
