package cbcoder.dealerwebapp.UsersInfo.model;

import cbcoder.dealerwebapp.UsersInfo.model.enums.RoleEnum;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "ROLES")
@SequenceGenerator(name = "roles_seq", sequenceName = "roles_seq", allocationSize = 1)
public class Role implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_seq")
	@Column(name = "ROLE_ID", updatable = false, nullable = false)
	private Integer roleId;

	@Column(name = "ROLE_NAME")
	@Enumerated(EnumType.STRING)
	private RoleEnum roleName;


	public Role() {
	}

	public Role(RoleEnum roleName) {
		this.roleName = roleName;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public RoleEnum getRoleName() {
		return roleName;
	}

	public void setRoleName(RoleEnum roleName) {
		this.roleName = roleName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Role role)) return false;
		return Objects.equals(getRoleId(), role.getRoleId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getRoleId());
	}

	@Override
	public String toString() {
		return "Role{" +
				"roleId=" + roleId +
				", roleName=" + roleName +
				'}';
	}
}
