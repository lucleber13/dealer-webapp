package cbcoder.dealerwebapp.UsersInfo.security;

import cbcoder.dealerwebapp.UsersInfo.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * <p>Copyright (c) 2024, Cleber Balbinote.</p>
 * <p>All rights reserved.</p>
 * <p>Licensed under the MIT License.</p>
 * <p>For full license text, please see the LICENSE file in the repo root or <a href="https://opensource.org/licenses/MI">...</a>T</p>
 * </br>
 * AuthUser class implements UserDetails interface and provides the user details to the Spring Security framework.
 * It is used to authenticate and authorize the user.
 * It provides the user details like username, password, authorities, account status, etc.
 * It is used to authenticate the user and check the user's roles and permissions.
 *
 * @author Cleber Balbinote
 * @version 1.0
 * @see UserDetails
 * @see User
 * @see GrantedAuthority
 * @see SimpleGrantedAuthority
 * @since 2024-06-15
 */
public class AuthUser implements UserDetails {

    private final User user;

    public AuthUser(User user) {
        this.user = user;
    }

    /**
     * This method returns the user's authorities.
     * It maps the user's roles to the authorities.
     *
     * @return Collection<? extends GrantedAuthority> - The user's authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Map the user's roles to the authorities and return them.
        return this.user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                .collect(Collectors.toList());
    }

    /**
     * This method returns the user's password.
     *
     * @return String - The user's password.
     */
    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    /**
     * This method returns the user's username.
     * In this case, the username is the user's email.
     *
     * @return String - The user's email.
     */
    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

    /**
     * This method returns the user's account status.
     * It checks if the user's account is not expired, not locked, not disabled, and not credentials expired.
     *
     * @return boolean - The user's account status.
     */
    @Override
    public boolean isEnabled() {
        return this.user.isEnabled();
    }
}
