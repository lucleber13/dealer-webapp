package cbcoder.dealerwebapp.UsersInfo.Dtos;

import cbcoder.dealerwebapp.UsersInfo.model.Role;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public class UserDto {
	private Long userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private boolean isEnabled;
	@CreatedDate
	private LocalDateTime createdAt = LocalDateTime.now();
	@LastModifiedDate
	private LocalDateTime updatedAt;
	private Set<Role> roles;

	public UserDto() {
	}

	public UserDto(Long userId, String firstName, String lastName, String email, String password, boolean isEnabled, Set<Role> roles) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.isEnabled = isEnabled;
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

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean enabled) {
		isEnabled = enabled;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof UserDto user)) return false;
		return Objects.equals(getUserId(), user.getUserId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getUserId());
	}

	@Override
	public String toString() {
		return "UserDto{" +
				"id=" + userId +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", isEnabled=" + isEnabled +
				", createdAt=" + createdAt +
				", updatedAt=" + updatedAt +
				", roles=" + roles +
				'}';
	}

}
