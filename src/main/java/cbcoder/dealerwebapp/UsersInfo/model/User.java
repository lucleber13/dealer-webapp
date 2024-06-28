package cbcoder.dealerwebapp.UsersInfo.model;

import cbcoder.dealerwebapp.Cars.model.Car;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * <p>Copyright (c) 2024, Cleber Balbinote.</p>
 * <p>All rights reserved.</p>
 * <p>Licensed under the MIT License.</p>
 * <p>For full license text, please see the LICENSE file in the repo root or <a href="https://opensource.org/licenses/MI">...</a>T</p>
 * </br>
 * User class represents the User entity.
 * It is mapped to the USERS table in the database.
 * The class is annotated with the @Entity annotation to indicate that it is a JPA entity.
 * The @Table annotation specifies the name of the table in the database.
 * The class is also annotated with the @SequenceGenerator annotation
 * to generate the primary key values which started initial value 2.
 * The class implements the Serializable interface to make the User objects serializable.
 * The class has fields for the user id, first name, last name, email, password, isEnabled, createdAt, updatedAt, roles, and cars.
 *
 * @author Cleber Balbinote
 * @version 1.0
 * @see Role
 * @see Car
 * @since 2024-06-15
 */
@Entity
@Table(name = "USERS")
@SequenceGenerator(name = "users_seq", sequenceName = "users_seq", allocationSize = 1, initialValue = 2)
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @Column(name = "USER_ID", updatable = false, nullable = false)
    private Long userId;
    @Column(name = "FIRST_NAME", nullable = false)
    @NotNull(message = "First name is required")
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    @NotNull(message = "Last name is required")
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 100, message = "Last name must be between 2 and 100 characters")
    private String lastName;

    @Column(name = "EMAIL", nullable = false, unique = true)
    @NotNull(message = "Email is required")
    @NotBlank(message = "Email is required")
    @Size(min = 5, max = 255, message = "Email must be between 5 and 255 characters")
    @Email(message = "Email must be a valid email address")
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    @NotNull(message = "Password is required")
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    @JsonIgnore
    private String password;

    @Column(name = "IS_ENABLED")
    private boolean isEnabled;

    @Column(name = "CREATED_AT", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "UPDATED_AT")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USERS_roles",
            joinColumns = @JoinColumn(name = "user_USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "roles_ROLE_ID"))
    private Set<Role> roles = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    private Set<Car> cars = new LinkedHashSet<>();

    public User() {
    }

    public User(String firstName, String lastName, String email, String password, boolean isEnabled) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isEnabled = isEnabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotNull(message = "First name is required") @NotBlank(message = "First name is required") @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters") String firstName) {
        this.firstName = firstName.toUpperCase().charAt(0) + firstName.substring(1).toLowerCase();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NotNull(message = "Last name is required") @NotBlank(message = "Last name is required") @Size(min = 2, max = 100, message = "Last name must be between 2 and 100 characters") String lastName) {
        String[] names = lastName.split(" ");
        if (names.length > 1) {
            StringBuilder lastNameBuilder = new StringBuilder();
            for (String name : names) {
                lastNameBuilder.append(name.toUpperCase().charAt(0)).append(name.substring(1).toLowerCase()).append(" ");
            }
            this.lastName = lastNameBuilder.toString().trim();
            return;
        }
        this.lastName = lastName.toUpperCase().charAt(0) + lastName.substring(1).toLowerCase();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(@NotNull(message = "Email is required") @NotBlank(message = "Email is required") @Size(min = 5, max = 255, message = "Email must be between 5 and 255 characters") @Email(message = "Email must be a valid email address") String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(@NotNull(message = "Password is required") @NotBlank(message = "Password is required") @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters") String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getUserId(), user.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUserId());
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isEnabled=" + isEnabled +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", roles=" + roles +
                ", cars=" + cars +
                '}';
    }
}
